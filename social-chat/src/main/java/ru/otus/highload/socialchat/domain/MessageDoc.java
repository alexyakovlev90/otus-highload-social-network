package ru.otus.highload.socialchat.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Document
@CompoundIndexes({
        @CompoundIndex(name = "chatId_dateCreated", def = "{'chatId' : 1, 'dateCreated': -1}")
})
public class MessageDoc {

    @Id
    private String id;

    private String chatId;
    private Long fromUser;
    private Long dateCreated;
    private String text;
}
