package ru.otus.highload.socialcounter.feature;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.util.rest.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-counter")
public class CounterController {

    private final CounterService counterService;

    @PutMapping("/incr")
    public Response<Long> incrChats(@RequestParam("userId") Long userId) {
        return new Response<>(counterService.incrCounter(userId, null));
    }

    @PutMapping("/decr")
    public Response<Long> decrChats(@RequestParam("userId") Long userId) {
        return new Response<>(counterService.decrCounter(userId, null));
    }

    @PostMapping("/reset")
    public Response<Void> resetChats(@RequestParam("userId") Long userId) {
        counterService.resetCounter(userId, null);
        return new Response<>();
    }
}
