package ru.otus.highload.socialchat.feature.message;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.highload.grpc.CounterGrpcService;
import ru.otus.highload.grpc.MessageCounterGrpcServiceGrpc.MessageCounterGrpcServiceBlockingStub;
import ru.otus.highload.socialchat.domain.ChatDoc;
import ru.otus.highload.socialchat.domain.MessageDoc;
import ru.otus.highload.socialchat.feature.chat.ChatRepository;
import ru.otus.highload.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageCounterGrpcServiceBlockingStub messageCounterGrpcServiceBlockingStub;

    @Timed
    public List<MessageDoc> getMessages(String chatId, Long fromDate, Integer count) {
        if (fromDate == null || fromDate == 0L) {
            return messageRepository.findByChatId(chatId);
        }

        LocalDate dateCreated = DateTimeUtil.fromMilliseconds(fromDate).toLocalDate();
        return messageRepository.getByChatIdAndDateCreatedAfter(chatId, dateCreated)
                .limit(count != null ? count : 50)
                .collect(Collectors.toList());
    }

    @Timed
    public MessageDoc createMessage(String chatId, Long from, Long date, String text) {
        MessageDoc messageDoc = new MessageDoc()
                .setChatId(chatId)
                .setDateCreated(date != null ? date : System.currentTimeMillis())
                .setFromUser(from)
                .setText(text);
        return messageRepository.insert(messageDoc);
    }

    @Timed
    public MessageDoc createChat(String chatId, Long fromUser, Long date, String text) {
        // Проверка, существует ли чат
        Optional<ChatDoc> chatOpt = chatRepository.findById(chatId);
        if (chatOpt.isEmpty()) {
            log.warn("Users chat not exists. chatId={}", chatId);
            return null;
        }

        // Создание нового чата
        MessageDoc messageDoc = new MessageDoc()
                .setChatId(chatId)
                .setDateCreated(date != null ? date : System.currentTimeMillis())
                .setFromUser(fromUser)
                .setText(text);
        MessageDoc insert = messageRepository.insert(messageDoc);
        // инкремент счетчиков
        Long fromUserChat = chatOpt.get().getFromUser();
        try {
            incrMessagesCounter(fromUserChat, chatId);
        } catch (Exception e) {
            // компенсация
            log.error("Unable to increment counter for user={}, compensate chat insert", fromUserChat, e);
            messageRepository.delete(insert);
            return null;
        }

        Long toUserChat = chatOpt.get().getToUser();
        try {
            incrMessagesCounter(toUserChat, chatId);
        } catch (Exception e) {
            // компенсация
            log.error("Unable to increment counter for user={}, compensate chat insert", toUserChat, e);
            messageRepository.delete(insert);
            decrMessagesCounter(fromUserChat, chatId);
            return null;
        }
        log.info("New Chat created={}", insert);
        return insert;
    }

    private long incrMessagesCounter(Long userId, String chatId) {
        CounterGrpcService.UserChatRequest req = CounterGrpcService.UserChatRequest.newBuilder()
                .setUserId(userId)
                .setChat(chatId)
                .build();
        CounterGrpcService.CounterResp counterResp = messageCounterGrpcServiceBlockingStub.incrMessages(req);
        long counter = counterResp.getCounter();
        log.info("increment messages counter={} for user={}", counter, userId);
        return counter;
    }

    private long decrMessagesCounter(Long userId, String chatId) {
        CounterGrpcService.UserChatRequest req = CounterGrpcService.UserChatRequest.newBuilder()
                .setUserId(userId)
                .setChat(chatId)
                .build();
        CounterGrpcService.CounterResp counterResp = messageCounterGrpcServiceBlockingStub.decrMessages(req);
        long counter = counterResp.getCounter();
        log.info("decrement messages counter={} for user={}", counter, userId);
        return counter;
    }
}
