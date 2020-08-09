package ru.otus.highload.socialbackend.feature.messaging.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.otus.highload.grpc.ChatGrpcServiceGrpc;
import ru.otus.highload.grpc.ChatGrpcServiceOuterClass;
import ru.otus.highload.grpc.ChatGrpcServiceOuterClass.ChatGetRequest;
import ru.otus.highload.grpc.ChatGrpcServiceOuterClass.ChatPostRequest;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatGrpcConverter chatGrpcConverter;

    @GrpcClient("MessageGrpcService")
    private ChatGrpcServiceGrpc.ChatGrpcServiceBlockingStub chatGrpcService;


    public List<ChatCoreDto> getUserChats(Long userId) {
        ChatGetRequest req = ChatGetRequest.newBuilder()
                .setUserId(userId)
                .build();

        log.info("Query chats for User={}", userId);
        return chatGrpcService.getChats(req)
                .getChatsList().stream()
                .map(chatGrpcConverter::convert)
                .collect(Collectors.toList());
    }

    public ChatCoreDto createChat(Long fromUser, Long toUser, Long date) {
        ChatPostRequest req = ChatPostRequest.newBuilder()
                .setFromUser(fromUser)
                .setToUser(toUser)
                .setDateCreated(date)
                .build();

        log.info("Create new Chat for fromUser={}, toUser={}", fromUser, toUser);
        ChatGrpcServiceOuterClass.ChatDto chat = chatGrpcService.postChat(req).getChat();
        return chatGrpcConverter.convert(chat);
    }

}
