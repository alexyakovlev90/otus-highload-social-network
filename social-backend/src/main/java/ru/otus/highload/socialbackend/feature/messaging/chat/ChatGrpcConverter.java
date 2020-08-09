package ru.otus.highload.socialbackend.feature.messaging.chat;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static ru.otus.highload.grpc.ChatGrpcServiceOuterClass.*;

@Component
public class ChatGrpcConverter implements Converter<ChatDto, ChatCoreDto> {

    @Override
    public ChatCoreDto convert(ChatDto chatDto) {
        return new ChatCoreDto()
                .setId(chatDto.getId())
                .setFromUser(chatDto.getFromUser())
                .setToUser(chatDto.getToUser())
                .setDateCreated(chatDto.getDateCreated());
    }
}
