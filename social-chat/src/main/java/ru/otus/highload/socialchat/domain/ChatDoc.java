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
        @CompoundIndex(name = "fromUser_dateCreated", def = "{'fromUser' : 1, 'dateCreated': -1}")
})
public class ChatDoc {

    @Id
    private String id;

    private Long fromUser;
    private Long toUser;
    private Long dateCreated;
}
