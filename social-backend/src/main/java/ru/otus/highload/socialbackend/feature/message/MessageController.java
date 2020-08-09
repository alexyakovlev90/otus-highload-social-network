package ru.otus.highload.socialbackend.feature.message;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.util.rest.response.ListResponse;
import ru.otus.highload.util.rest.response.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ListResponse<MessageCoreDto> getMessages(@RequestParam("chatId") String chatId,
                                                @RequestParam(value = "fromDate", required = false) Long fromDate,
                                                @RequestParam(value = "count", required = false) Integer count) {
        List<MessageCoreDto> messages = messageService.getMessages(chatId, fromDate, count);
        return new ListResponse<>(messages);
    }

    @PostMapping
    public Response<MessageCoreDto> createMessage(@RequestParam("chatId") String chatId,
                                              @RequestParam("fromUser") Long fromUser,
                                              @RequestParam(value = "date", required = false) Long date,
                                              @RequestParam("text") String text) {
        MessageCoreDto message = messageService.createMessage(chatId, fromUser, date, text);
        return new Response<>(message);
    }

}
