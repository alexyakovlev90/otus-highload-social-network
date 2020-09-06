package ru.otus.highload.socialchat.feature.chat;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.highload.grpc.ChatCounterGrpcServiceGrpc.ChatCounterGrpcServiceBlockingStub;
import ru.otus.highload.grpc.CounterGrpcService.CounterResp;
import ru.otus.highload.grpc.CounterGrpcService.UserRequest;
import ru.otus.highload.socialchat.domain.ChatDoc;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatCounterGrpcServiceBlockingStub chatCounterGrpcServiceBlockingStub;

    @Timed
    public List<ChatDoc> getUserChats(Long userId) {
//        return chatRepository.findByFromUser(userId);
        return chatRepository.findUserChats(userId);
    }

    @Timed
    public ChatDoc createChat(Long fromUser, Long toUser, Long date) {
        // Проверка, существует ли чат
        boolean usersChatExists = chatRepository.checkUsersChat(fromUser, toUser);
        if (usersChatExists) {
            log.warn("Users chat already exists. From={}, to={}", fromUser, toUser);
            return null;
        }

        // Создание нового чата
        ChatDoc chatDoc = new ChatDoc()
                .setFromUser(fromUser)
                .setToUser(toUser)
                .setDateCreated(date != null ? date : System.currentTimeMillis());
        ChatDoc insert = chatRepository.insert(chatDoc);
        // инкремент счетчиков
        try {
            incrChats(fromUser);
        } catch (Exception e) {
            // компенсация
            log.error("Unable to increment counter for user={}, compensate chat insert", fromUser, e);
            chatRepository.delete(insert);
            return null;
        }

        try {
            incrChats(toUser);
        } catch (Exception e) {
            // компенсация
            log.error("Unable to increment counter for user={}, compensate chat insert", toUser, e);
            chatRepository.delete(insert);
            decrChatCounter(fromUser);
            return null;
        }
        log.info("New Chat created={}", insert);
        return insert;
    }

    private long incrChats(Long userId) {
        log.info("increment chats counter for user={}", userId);
        UserRequest req = UserRequest.newBuilder()
                .setUserId(userId)
                .build();
        CounterResp counterResp = chatCounterGrpcServiceBlockingStub.incrChats(req);
        long counter = counterResp.getCounter();
        log.info("increment chats counter={} for user={}", counter, userId);
        return counter;
    }

    private long decrChatCounter(Long userId) {
        log.info("decrement chats counter for user={}", userId);
        UserRequest req = UserRequest.newBuilder()
                .setUserId(userId)
                .build();
        CounterResp counterResp = chatCounterGrpcServiceBlockingStub.decrChats(req);
        long counter = counterResp.getCounter();
        log.info("decrement chats counter={} for user={}", counter, userId);
        return counter;
    }
}
