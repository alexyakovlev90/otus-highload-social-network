package ru.otus.highload.socialbackend.feature.wall_post.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.otus.highload.socialbackend.domain.WallPost;
import ru.otus.highload.socialbackend.feature.wall_post.RedisService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Log4j2
@RequiredArgsConstructor
public class RabbitWallPostDeliveryCallback implements DeliverCallback {

    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Override
    public void handle(String consumerTag, Delivery delivery) throws IOException {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        log.info(" [x] Received message '{}' with routing key {}", message, delivery.getEnvelope().getRoutingKey());
        // read json message
        WallPost evictMessage = objectMapper.readValue(message, WallPost.class);
    }
}
