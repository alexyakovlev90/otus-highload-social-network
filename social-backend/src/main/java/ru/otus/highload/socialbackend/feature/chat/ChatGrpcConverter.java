package ru.otus.highload.socialbackend.feature.chat;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.highload.grpc.ChatGrpcServiceOuterClass;
import ru.otus.highload.socialbackend.feature.message.MessageCoreDto;

import static ru.otus.highload.grpc.ChatGrpcServiceOuterClass.*;
import static ru.otus.highload.grpc.ChatGrpcServiceOuterClass.MessageDto;

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
