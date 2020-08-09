package ru.otus.highload.socialbackend.feature.messaging.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.otus.highload.grpc.MessageGrpcServiceGrpc;

import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.highload.grpc.ChatGrpcServiceOuterClass.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    @GrpcClient("MessageGrpcService")
    private MessageGrpcServiceGrpc.MessageGrpcServiceBlockingStub messageGrpcService;

    private final MessageGrpcConverter messageGrpcConverter;

    public List<MessageCoreDto> getMessages(String chatId, Long fromDate, Integer count) {
        MessageGetRequest req = MessageGetRequest.newBuilder()
                .setChatId(chatId)
                .setFromDate(fromDate)
                .setMessagesCount(count)
                .build();

        log.info("Get messages for Chat={}, fromDate={}, messageCount={}", chatId, fromDate, count);
        return messageGrpcService.getMessages(req)
                .getMessagesList().stream()
                .map(messageGrpcConverter::convert)
                .collect(Collectors.toList());
    }
    public MessageCoreDto createMessage(String chatId, Long fromUser, Long date, String text) {
        MessagePostRequest req = MessagePostRequest.newBuilder()
                .setChatId(chatId)
                .setFromUserId(fromUser)
                .setDate(date)
                .setText(text)
                .build();

        log.info("Create new message for Chat={}, fromUser={}", chatId, fromUser);
        MessageDto message = messageGrpcService.postMessage(req)
                .getMessage();
        return messageGrpcConverter.convert(message);
    }
}
