package ru.otus.highload.socialchat.feature.message;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialchat.domain.MessageDoc;
import ru.otus.highload.util.rest.response.ListResponse;
import ru.otus.highload.util.rest.response.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Timed
    @GetMapping
    public ListResponse<MessageDoc> getMessages(@RequestParam("chatId") String chatId,
                                                @RequestParam(value = "fromDate", required = false) Long fromDate,
                                                @RequestParam(value = "count", required = false) Integer count) {
        List<MessageDoc> messages = messageService.getMessages(chatId, fromDate, count);
        return new ListResponse<>(messages);
    }

    @Timed
    @PostMapping
    public Response<MessageDoc> createMessage(@RequestParam("chatId") String chatId,
                                              @RequestParam("fromUser") Long fromUser,
                                              @RequestParam(value = "date", required = false) Long date,
                                              @RequestParam("text") String text) {
        MessageDoc message = messageService.createMessage(chatId, fromUser, date, text);
        return new Response<>(message);
    }

}
