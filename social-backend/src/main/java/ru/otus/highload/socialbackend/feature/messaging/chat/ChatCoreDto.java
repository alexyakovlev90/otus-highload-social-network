package ru.otus.highload.socialbackend.feature.messaging.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ChatCoreDto {

    private String id;

    private Long fromUser;
    private Long toUser;
    private Long dateCreated;
}
