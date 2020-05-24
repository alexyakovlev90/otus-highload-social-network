package ru.otus.highload.socialchat.feature.message;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.highload.socialchat.domain.MessageDoc;

import java.time.LocalDate;
import java.util.stream.Stream;

@Repository
public interface MessageRepository extends MongoRepository<MessageDoc, String> {

    @Query(value="{chatId: ?0, dateCreated: {'$gt': ?1}}",
            sort="{'dateCreated': -1}")
    Stream<MessageDoc> getByChatIdAndDateCreatedAfter(String chatId, LocalDate dateCreated);
}
