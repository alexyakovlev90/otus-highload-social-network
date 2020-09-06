package ru.otus.highload.socialchat.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.highload.grpc.ChatCounterGrpcServiceGrpc;
import ru.otus.highload.grpc.ChatCounterGrpcServiceGrpc.ChatCounterGrpcServiceBlockingStub;
import ru.otus.highload.grpc.ChatGrpcServiceGrpc;
import ru.otus.highload.grpc.MessageCounterGrpcServiceGrpc;
import ru.otus.highload.grpc.MessageCounterGrpcServiceGrpc.MessageCounterGrpcServiceBlockingStub;
import ru.otus.highload.grpc.MessageGrpcServiceGrpc;

@Configuration
@RequiredArgsConstructor
public class CounterGrpcClientConfig {

//    private final DiscoveryClient discoveryClient;

    @Value("${grpc.counter.address}")
    private String chatAddress;

    @Value("${grpc.counter.port}")
    private Integer chatPort;

    @Bean
    public ChatCounterGrpcServiceBlockingStub chatGrpcServiceGrpc() {
//        ServiceInstance chatGrpcInstance = chatGrpcInstance();

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(chatAddress, chatPort)
//                .forAddress(chatGrpcInstance.getHost(), chatGrpcInstance.getPort())
                .usePlaintext()
                .build();
        return ChatCounterGrpcServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public MessageCounterGrpcServiceBlockingStub messageGrpcServiceGrpc() {
//        ServiceInstance chatGrpcInstance = chatGrpcInstance();
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(chatAddress, chatPort)
//                .forAddress(chatGrpcInstance.getHost(), chatGrpcInstance.getPort())
                .usePlaintext()
                .build();
        return MessageCounterGrpcServiceGrpc.newBlockingStub(channel);
    }

//    private ServiceInstance chatGrpcInstance() {
//        return discoveryClient.getInstances("chat_app_grpc")
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("Unable to discover a mysql instance"));
//    }
}
