package ru.otus.highload.socialbackend.feature.message;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static ru.otus.highload.grpc.ChatGrpcServiceOuterClass.MessageDto;

@Component
public class MessageGrpcConverter implements Converter<MessageDto, MessageCoreDto> {

    @Override
    public MessageCoreDto convert(MessageDto messageDoc) {
        return new MessageCoreDto()
                .setId(messageDoc.getId())
                .setFromUser(messageDoc.getFromUser())
                .setDateCreated(messageDoc.getDateCreated())
                .setText(messageDoc.getText());
    }
}
