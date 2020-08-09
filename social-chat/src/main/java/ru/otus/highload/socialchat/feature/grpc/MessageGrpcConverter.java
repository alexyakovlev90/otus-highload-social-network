package ru.otus.highload.socialchat.feature.grpc;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.highload.socialchat.domain.MessageDoc;

import static ru.otus.highload.grpc.ChatGrpcServiceOuterClass.MessageDto;

@Component
public class MessageGrpcConverter implements Converter<MessageDoc, MessageDto> {

    @Override
    public MessageDto convert(MessageDoc messageDoc) {
        return MessageDto.newBuilder()
                .setId(messageDoc.getId())
                .setFromUser(messageDoc.getFromUser())
                .setDateCreated(messageDoc.getDateCreated())
                .setText(messageDoc.getText())
                .build();
    }
}
