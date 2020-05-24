package ru.otus.highload.socialchat.feature.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialchat.domain.MessageDoc;
import ru.otus.highload.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<MessageDoc> getMessages(String chatId, String fromDate, Integer count) {
        LocalDate dateCreated = DateTimeUtil.parseIsoDate(fromDate);
        return messageRepository.getByChatIdAndDateCreatedAfter(chatId, dateCreated)
                .limit(count)
                .collect(Collectors.toList());
    }

    public MessageDoc createMessage(String chatId, Long from, Long date, String text) {
        MessageDoc messageDoc = new MessageDoc()
                .setChatId(chatId)
                .setDateCreated(date)
                .setFromUser(from)
                .setText(text);
        return messageRepository.insert(messageDoc);
    }
}
