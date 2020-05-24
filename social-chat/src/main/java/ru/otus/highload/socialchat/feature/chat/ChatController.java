package ru.otus.highload.socialchat.feature.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialchat.domain.ChatDoc;
import ru.otus.highload.util.rest.response.ListResponse;
import ru.otus.highload.util.rest.response.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/user")
    public ListResponse<ChatDoc> getUserChats(@RequestParam("userId") Long userId) {
        List<ChatDoc> userChats = chatService.getUserChats(userId);
        return new ListResponse<>(userChats);
    }

    @PostMapping()
    public Response<ChatDoc> createChat(@RequestParam("fromUser") Long fromUser, @RequestParam("toUser") Long toUser,
                                        @RequestParam("date") Long date) {
        ChatDoc userChats = chatService.createChat(fromUser, toUser, date);
        return new Response<>(userChats);
    }

}
