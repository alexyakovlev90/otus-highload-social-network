package ru.otus.highload.socialcounter.feature;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.otus.highload.grpc.CounterGrpcService;
import ru.otus.highload.grpc.CounterGrpcService.CounterResp;
import ru.otus.highload.grpc.CounterGrpcService.EmptyResp;
import ru.otus.highload.grpc.MessageCounterGrpcServiceGrpc;


@GrpcService
@AllArgsConstructor
@Slf4j
public class MessageCounterGrpcServiceImpl extends MessageCounterGrpcServiceGrpc.MessageCounterGrpcServiceImplBase {

    private final CounterService counterService;

    @Override
    public void incrMessages(CounterGrpcService.UserChatRequest request, StreamObserver<CounterResp> responseObserver) {
        log.info("Query incrMessages for User={}, Chat={}", request.getUserId(), request.getChat());
        try {
            long userId = request.getUserId();
            String chat = request.getChat();
            Long counter = counterService.incrCounter(userId, chat);
            CounterResp reply = CounterResp.newBuilder()
                    .setCounter(counter).build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error Query incrMessages for User={}, Chat={}", request.getUserId(), request.getChat(), e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void decrMessages(CounterGrpcService.UserChatRequest request, StreamObserver<CounterResp> responseObserver) {
        log.info("Query decrMessages for User={}, Chat={}", request.getUserId(), request.getChat());
        try {
            long userId = request.getUserId();
            String chat = request.getChat();
            Long counter = counterService.decrCounter(userId, chat);
            CounterResp reply = CounterResp.newBuilder()
                    .setCounter(counter).build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error Query decrMessages for User={}, Chat={}", request.getUserId(), request.getChat(), e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void resetMessages(CounterGrpcService.UserChatRequest request, StreamObserver<EmptyResp> responseObserver) {
        log.info("Query decrMessages for User={}, Chat={}", request.getUserId(), request.getChat());
        try {
            long userId = request.getUserId();
            String chat = request.getChat();
            counterService.resetCounter(userId, chat);
            EmptyResp reply = EmptyResp.newBuilder().build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error Query decrMessages for User={}, Chat={}", request.getUserId(), request.getChat(), e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

}
