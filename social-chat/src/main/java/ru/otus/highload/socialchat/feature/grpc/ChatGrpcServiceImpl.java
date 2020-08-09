package ru.otus.highload.socialchat.feature.grpc;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.otus.highload.grpc.ChatGrpcServiceGrpc;
import ru.otus.highload.socialchat.domain.ChatDoc;
import ru.otus.highload.socialchat.feature.chat.ChatService;
import ru.otus.highload.socialchat.feature.message.MessageService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.highload.grpc.ChatGrpcServiceOuterClass.*;


@GrpcService
@AllArgsConstructor
@Slf4j
public class ChatGrpcServiceImpl extends ChatGrpcServiceGrpc.ChatGrpcServiceImplBase {

    private final ChatService chatService;
    private final MessageService messageService;
    private final MessageGrpcConverter messageGrpcConverter;

    @Override
    public void getChats(ChatGetRequest request, StreamObserver<ChatGetResponse> responseObserver) {
        log.info("Query chats for User={}", request.getUserId());
        try {

            long userId = request.getUserId();
            int messagesCount = request.getMessagesCount();

            List<ChatDto> chatDtos = chatService.getUserChats(userId).stream()
                    .map(chatDoc -> chatDocToChatDto(chatDoc, messagesCount))
                    .collect(Collectors.toList());

            ChatGetResponse reply = ChatGetResponse
                    .newBuilder()
                    .addAllChats(chatDtos)
                    .build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error while Query chats for User={}", request.getUserId(), e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void postChat(ChatPostRequest request, StreamObserver<ChatPostResponse> responseObserver) {
        long fromUser = request.getFromUser();
        long toUser = request.getToUser();
        log.info("Create new Chat for fromUser={}, toUser={}", fromUser, toUser);
        try {
            ChatDoc chat = chatService.createChat(fromUser, toUser, request.getDateCreated());

            ChatPostResponse reply = ChatPostResponse
                    .newBuilder()
                    .setChat(chatDocToChatDto(chat, 0))
                    .build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error while Create new Chat for fromUser={}, toUser={}", request.getFromUser(), request.getToUser(), e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

    private ChatDto chatDocToChatDto(ChatDoc chatDoc, int messageCount) {
        ChatDto.Builder builder = ChatDto.newBuilder().setId(chatDoc.getId())
                .setToUser(chatDoc.getToUser())
                .setFromUser(chatDoc.getFromUser())
                .setDateCreated(chatDoc.getDateCreated());

        if (messageCount > 0) {
            messageService.getMessages(chatDoc.getId(), null, messageCount).stream()
                    .map(messageGrpcConverter::convert)
                    .forEach(builder::addMessages);

        }
        return builder.build();
    }
}
