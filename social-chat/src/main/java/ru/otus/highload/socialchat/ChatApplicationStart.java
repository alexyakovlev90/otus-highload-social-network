package ru.otus.highload.socialchat;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.otus.highload.socialchat.domain.ChatDoc;
import ru.otus.highload.socialchat.feature.chat.ChatRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class ChatApplicationStart implements ApplicationListener<ContextRefreshedEvent> {

    private final ChatRepository chatRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ChatDoc chatDoc = new ChatDoc()
                .setFromUser(1L)
                .setToUser(2L)
                .setDateCreated(System.currentTimeMillis());
//                .setId(UUID.randomUUID().toString());

        ChatDoc insert = chatRepository.insert(chatDoc);
        System.out.println(insert);

        List<ChatDoc> all = chatRepository.findAll();
        System.out.println(all);
    }

}
