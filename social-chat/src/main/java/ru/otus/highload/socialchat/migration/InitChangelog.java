package ru.otus.highload.socialchat.migration;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.highload.socialchat.domain.ChatDoc;
import ru.otus.highload.socialchat.domain.MessageDoc;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ChangeLog
public class InitChangelog {

    @ChangeSet(order = "001", id = "initDb", author = "titkin")
    public void initDta(MongoTemplate mongoTemplate) {
        Random rand = new Random();
        List<ChatDoc> chats = IntStream.range(0, 10)
                .mapToObj(num -> new ChatDoc()
                        .setFromUser(getRandomLong(rand))
                        .setToUser(getRandomLong(rand))
                        .setDateCreated(System.currentTimeMillis()))
                .map(mongoTemplate::save)
                .collect(Collectors.toList());

        for (ChatDoc chat : chats) {
            MessageDoc req = new MessageDoc()
                    .setChatId(chat.getId())
                    .setDateCreated(System.currentTimeMillis())
                    .setFromUser(chat.getFromUser())
                    .setText("Привет! Как дела?");
            mongoTemplate.save(req);

            MessageDoc resp = new MessageDoc()
                    .setChatId(chat.getId())
                    .setDateCreated(System.currentTimeMillis())
                    .setFromUser(chat.getToUser())
                    .setText("Норм, как сам?");
            mongoTemplate.save(resp);
        }
    }

    private long getRandomLong(Random rand) {
        return Integer.valueOf(rand.nextInt(100)).longValue();
    }
}
