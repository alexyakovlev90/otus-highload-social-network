package ru.otus.highload.socialchat.feature.chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.highload.socialchat.domain.ChatDoc;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<ChatDoc, String> {

    @Query(value="{fromUser: ?0}",
            sort="{'dateCreated': -1}")
    List<ChatDoc> findByFromUser(Long fromUser);

    //{$or : [{name : "Name1"}, {name : "Name2"}]}
    @Query(value="{$or : [{fromUser: ?0}, {toUser: ?0}]}",
            sort="{'dateCreated': -1}")
    List<ChatDoc> findUserChats(Long user);

    @Query(value="{$or : [" +
            "{$and : [{fromUser: ?1}, {toUser: ?0}]}, " +
            "{$and : [{fromUser: ?0}, {toUser: ?1}]}" +
            "]}",
            exists = true)
    boolean checkUsersChat(Long first, Long second);
}
