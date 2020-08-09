package ru.otus.highload.socialbackend.feature.messaging.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.util.rest.response.ListResponse;
import ru.otus.highload.util.rest.response.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/user")
    public ListResponse<ChatCoreDto> getUserChats(@RequestParam("userId") Long userId) {
        List<ChatCoreDto> userChats = chatService.getUserChats(userId);
        return new ListResponse<>(userChats);
    }

    @PostMapping()
    public Response<ChatCoreDto> createChat(@RequestParam("fromUser") Long fromUser, @RequestParam("toUser") Long toUser,
                                        @RequestParam(value = "date", required = false) Long date) {
        ChatCoreDto userChats = chatService.createChat(fromUser, toUser, date);
        return new Response<>(userChats);
    }

}
