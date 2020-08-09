package ru.otus.highload.socialbackend.feature.messaging.message;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
public class MessageCoreDto {

    private String id;

    private String chatId;
    private Long fromUser;
    private Long dateCreated;
    private String text;
}
