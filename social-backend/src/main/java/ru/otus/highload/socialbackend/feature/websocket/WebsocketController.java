package ru.otus.highload.socialbackend.feature.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class WebsocketController {

    @MessageMapping("/lenta")
    @SendTo("/lenta-posts")
    public String getMessages(String message) {
        System.out.println(message);
        return message;
    }
}
