package ru.otus.highload.socialchat.feature.grpc;


import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.otus.highload.grpc.MessageGrpcServiceGrpc;
import ru.otus.highload.socialchat.domain.MessageDoc;
import ru.otus.highload.socialchat.feature.message.MessageService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.highload.grpc.ChatGrpcServiceOuterClass.*;

@GrpcService
@AllArgsConstructor
@Slf4j
public class MessageGrpcServiceImpl extends MessageGrpcServiceGrpc.MessageGrpcServiceImplBase {

    private final MessageGrpcConverter messageGrpcConverter;
    private final MessageService messageService;


    @Override
    public void getMessages(MessageGetRequest request, StreamObserver<MessageGetResponse> responseObserver) {
        String chatId = request.getChatId();
        long fromDate = request.getFromDate();
        int messagesCount = request.getMessagesCount();
        log.info("Get messages for Chat={}, fromDate={}, messageCount={}", chatId, fromDate, messagesCount);
        try {
            List<MessageDto> messageDtos = messageService.getMessages(chatId, fromDate, messagesCount).stream()
                    .map(messageGrpcConverter::convert)
                    .collect(Collectors.toList());

            MessageGetResponse reply = MessageGetResponse
                    .newBuilder()
                    .addAllMessages(messageDtos)
                    .build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error while Get messages for Chat={}, fromDate={}, messageCount={}", chatId, fromDate, messagesCount, e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();

    }

    @Override
    public void postMessage(MessagePostRequest request, StreamObserver<MessagePostResponse> responseObserver) {
        String chatId = request.getChatId();
        long fromUser = request.getFromUserId();
        log.info("Create new message for Chat={}, fromUser={}", chatId, fromUser);
        try {
            MessageDoc message = messageService.createMessage(chatId, fromUser, request.getDate(), request.getText());

            MessagePostResponse reply = MessagePostResponse
                    .newBuilder()
                    .setMessage(messageGrpcConverter.convert(message))
                    .build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error while Create new message for Chat={}, fromUser={}", chatId, fromUser, e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }
}
