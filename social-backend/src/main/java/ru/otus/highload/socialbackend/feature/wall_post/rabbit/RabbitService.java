package ru.otus.highload.socialbackend.feature.wall_post.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.domain.WallPost;
import ru.otus.highload.socialbackend.feature.user.UserInfoItemDto;
import ru.otus.highload.socialbackend.feature.user.UserService;
import ru.otus.highload.socialbackend.feature.wall_post.RedisService;
import ru.otus.highload.socialbackend.feature.websocket.WebSocketService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class RabbitService {

    public static final String DEFAULT_EXCHANGE = "EXCHANGE_TOPIC";

    //    private final RabbitWallPostDeliveryCallback rabbitWallPostDeliveryCallback;
    private final ConnectionFactory connectionFactory;
    private final RabbitChannelHolder rabbitChannelHolder;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private final WebSocketService webSocketService;

    @Lazy
    @Resource
    private UserService userService;

    @PostConstruct
    public void init() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(DEFAULT_EXCHANGE, BuiltinExchangeType.TOPIC);
        } catch (Exception e) {
            throw new RuntimeException("Unable to declare exchange", e);
        }
    }

    /**
     * Подписка на посты всех друзей
     * Необходима аутентификация
     * <p>
     * todo нужно хранить channel или connection в контексте пользователя,
     * закрывать при разлогине,
     * добавлять/убирать (channel.queueUnbind()) биндинги если добавлены друзья
     * subscribe при аутентификации
     * использовать SpringSecurity context или написать свою реализацию?
     */
    public void subscribeToFriendsPosts(User user) {
        List<Long> friends = userService.getUserFriends(user.getId())
                .stream()
                .map(UserInfoItemDto::getId)
                .collect(Collectors.toList());

        try {
            Channel channel = rabbitChannelHolder.getChannel(user);
            if (channel == null) {
                return;
            }
//            String queueName = channel.queueDeclare().getQueue();
            String queueName = rabbitChannelHolder.getQueueName(user);

            //  multiple binding ( routing keys ) to a single queue
            for (Long friendId : friends) {
                log.info("Subscribe to user({}) Posts", friendId.toString());
                channel.queueBind(queueName, DEFAULT_EXCHANGE, friendId.toString());
            }

            log.info("Start listen post events");
            channel.basicConsume(queueName, true, deliverCallback(user.getId()), consumerTag -> {

            });
        } catch (Exception e) {
            log.error("Unable to subscribe for wall posts");
            throw new RuntimeException("Unable to subscribe", e);
        }
    }

    /**
     * add user to subscriptions
     */
    public void addSubscription(Long userId) {
        log.info("Subscribe to user({}) Posts", userId);
        Channel channel = rabbitChannelHolder.getChannel(null);
        if (channel == null) {
            return;
        }

        try {
//            String queueName = channel.queueDeclare().getQueue();
            String queueName = rabbitChannelHolder.getQueueName(null);
            channel.queueBind(queueName, DEFAULT_EXCHANGE, userId.toString());
        } catch (Exception e) {
            log.error("Unable to subscribe for posts of user({})", userId);
            throw new RuntimeException("Unable to subscribe", e);
        }
    }

    public void removeSubscription(Long userId) {
        log.info("Unsubscribe to user({}) Posts", userId);

        Channel channel = rabbitChannelHolder.getChannel(null);
        if (channel == null) {
            return;
        }

        try {
            String queueName = channel.queueDeclare().getQueue();
            channel.queueUnbind(queueName, DEFAULT_EXCHANGE, userId.toString());
        } catch (Exception e) {
            log.error("Unable to subscribe for posts of user({})", userId);
            throw new RuntimeException("Unable to subscribe", e);
        }
    }

    /**
     * Publish to default exchange with toUserId as routing key
     */
    public void publish(WallPost wallPost) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(DEFAULT_EXCHANGE, BuiltinExchangeType.TOPIC);
            String json = objectMapper.writeValueAsString(wallPost);
            channel.basicPublish(DEFAULT_EXCHANGE, wallPost.getToUser().toString(), null, json.getBytes(StandardCharsets.UTF_8));
            log.info(" [x] Sent message '{}' as post on user={} Wall", json, wallPost.getToUser());
        } catch (Exception e) {
            log.error("Unable to send post message {}", wallPost);
            throw new RuntimeException("Unable to send post message", e);
        }
    }


    public DeliverCallback deliverCallback(Long userId) {
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info(" [x] Received message '{}' with routing key {}", message, delivery.getEnvelope().getRoutingKey());
            // read json message
            WallPost wallPost = objectMapper.readValue(message, WallPost.class);
            redisService.addPostToLenta(userId, wallPost);
            webSocketService.sendMessageToLenta(wallPost, userId);
        };
    }
}
