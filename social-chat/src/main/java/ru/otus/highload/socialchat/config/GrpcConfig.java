package ru.otus.highload.socialchat.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.highload.socialchat.feature.grpc.ChatGrpcServiceImpl;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class GrpcConfig {

//    private final ChatGrpcServiceImpl chatGrpcService;

//    @Bean
//    public GrpcAuthenticationReader grpcAuthenticationReader() {
//        return new CompositeGrpcAuthenticationReader(
//                Lists.newArrayList(new BasicGrpcAuthenticationReader()));
//    }

//    @Bean
//    public Server server() throws IOException {
//        return ServerBuilder.forPort(7070)
//                .addService(chatGrpcService)
//                .addService(ProtoReflectionService.newInstance())
//                .build()
//                .start();
//    }

    /**
     * For swagger https://github.com/grpc-swagger/grpc-swagger
     * @return
     */
    @Bean
    public GrpcServerConfigurer grpcServerConfigurer() {
        return serverBuilder -> serverBuilder.addService(ProtoReflectionService.newInstance());
    }

}
