package ru.otus.highload.socialcounter.feature;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.otus.highload.grpc.ChatCounterGrpcServiceGrpc;
import ru.otus.highload.grpc.CounterGrpcService;
import ru.otus.highload.grpc.CounterGrpcService.CounterResp;
import ru.otus.highload.grpc.CounterGrpcService.EmptyResp;
import ru.otus.highload.grpc.MessageCounterGrpcServiceGrpc;


@GrpcService
@AllArgsConstructor
@Slf4j
public class ChatCounterGrpcServiceImpl extends ChatCounterGrpcServiceGrpc.ChatCounterGrpcServiceImplBase {

    private final CounterService counterService;

    @Override
    public void incrChats(CounterGrpcService.UserRequest request, StreamObserver<CounterResp> responseObserver) {
        log.info("Query incrChats for User={}", request.getUserId());
        try {
            long userId = request.getUserId();
            Long counter = counterService.incrCounter(userId, null);
            CounterResp reply = CounterResp.newBuilder()
                    .setCounter(counter).build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error Query incrChats for User={}", request.getUserId(), e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void decrChats(CounterGrpcService.UserRequest request, StreamObserver<CounterResp> responseObserver) {
        log.info("Query decrChats for User={}", request.getUserId());
        try {
            long userId = request.getUserId();
            Long counter = counterService.decrCounter(userId, null);
            CounterResp reply = CounterResp.newBuilder()
                    .setCounter(counter).build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error Query decrChats for User={}", request.getUserId(), e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void resetChats(CounterGrpcService.UserRequest request, StreamObserver<EmptyResp> responseObserver) {
        log.info("Query resetChats for User={}", request.getUserId());
        try {
            long userId = request.getUserId();
            counterService.resetCounter(userId, null);
            EmptyResp reply = EmptyResp.newBuilder().build();
            responseObserver.onNext(reply);
        } catch (Exception e) {
            log.error("Error Query resetChats for User={}", request.getUserId(), e);
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

}
