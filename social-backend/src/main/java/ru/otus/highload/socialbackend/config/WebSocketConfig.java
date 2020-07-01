package ru.otus.highload.socialbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import ru.otus.highload.socialbackend.feature.websocket.WebSocketService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry confi) {
//        confi.enableSimpleBroker(WebSocketService.LENTA_TOPIC);
        confi.setApplicationDestinationPrefixes("/ws-topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/api")
                .setHandshakeHandler(new DefaultHandshakeHandler())
                .setAllowedOrigins("*")
                .withSockJS();

    }
}
