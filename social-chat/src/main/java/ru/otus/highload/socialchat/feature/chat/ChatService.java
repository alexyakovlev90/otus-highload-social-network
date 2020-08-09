package ru.otus.highload.socialchat.feature.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialchat.domain.ChatDoc;
import ru.otus.highload.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public List<ChatDoc> getUserChats(Long userId) {
//        return chatRepository.findByFromUser(userId);
        return chatRepository.findUserChats(userId);
    }

    public ChatDoc createChat(Long fromUser, Long toUser, Long date) {
        ChatDoc chatDoc = new ChatDoc()
                .setFromUser(fromUser)
                .setToUser(toUser)
                .setDateCreated(date != null ? date : System.currentTimeMillis());
        return chatRepository.insert(chatDoc);
    }
}
