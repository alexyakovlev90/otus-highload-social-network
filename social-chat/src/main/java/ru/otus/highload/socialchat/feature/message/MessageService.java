package ru.otus.highload.socialchat.feature.message;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialchat.domain.MessageDoc;
import ru.otus.highload.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

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
}
