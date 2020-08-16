package ru.otus.highload.socialbackend.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.highload.grpc.ChatGrpcServiceGrpc;
import ru.otus.highload.grpc.MessageGrpcServiceGrpc;

@Configuration
@RequiredArgsConstructor
public class GrpcChatClientConfig {

    private final DiscoveryClient discoveryClient;

    @Value("${grpc.chat.address}")
    private String chatAddress;

    @Value("${grpc.chat.port}")
    private Integer chatPort;

    @Bean
    public ChatGrpcServiceGrpc.ChatGrpcServiceBlockingStub chatGrpcServiceGrpc() {
        ServiceInstance chatGrpcInstance = chatGrpcInstance();

        ManagedChannel channel = ManagedChannelBuilder
//                .forAddress(chatAddress, chatPort)
                .forAddress(chatGrpcInstance.getHost(), chatGrpcInstance.getPort())
                .usePlaintext()
                .build();
        return ChatGrpcServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public MessageGrpcServiceGrpc.MessageGrpcServiceBlockingStub messageGrpcServiceGrpc() {
        ServiceInstance chatGrpcInstance = chatGrpcInstance();


        ManagedChannel channel = ManagedChannelBuilder
//                .forAddress(chatAddress, chatPort)
                .forAddress(chatGrpcInstance.getHost(), chatGrpcInstance.getPort())
                .usePlaintext()
                .build();
        return MessageGrpcServiceGrpc.newBlockingStub(channel);
    }

    private ServiceInstance chatGrpcInstance() {
        return discoveryClient.getInstances("chat_app_grpc")
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to discover a mysql instance"));
    }
}
