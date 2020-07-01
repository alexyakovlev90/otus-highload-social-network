package ru.otus.highload.socialbackend.feature.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.WallPost;
import ru.otus.highload.socialbackend.feature.wall_post.WallPostDto;
import ru.otus.highload.socialbackend.feature.wall_post.WallPostToDtoConverter;
import ru.otus.highload.util.rest.response.Response;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    public static final String LENTA_TOPIC = "/lenta";

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WallPostToDtoConverter wallPostToDtoConverter;

    /**
     * userId used as topic
     */
    public void sendMessageToLenta(WallPost wallPost, Long userId) {
        WallPostDto wallPostDto = wallPostToDtoConverter.convert(wallPost);
        Response<WallPostDto> response = new Response<>(wallPostDto);
        simpMessagingTemplate.convertAndSend(userId.toString(), response);
    }


}
