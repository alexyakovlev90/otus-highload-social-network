package ru.otus.highload.socialcounter.feature;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialcounter.config.RedisKeyDto;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final ValueOperations<RedisKeyDto, String> valueOperations;

    public Long incrCounter(Long userId, String chatId) {
        return valueOperations.increment(new RedisKeyDto(userId, chatId));
    }

    public Long decrCounter(Long userId, String chatId) {
        Long value = valueOperations.decrement(new RedisKeyDto(userId, chatId));
        if (value != null && value < 0) {
            resetCounter(userId, chatId);
            return 0L;
        }
        return value;
    }

    public String resetCounter(Long userId, String chatId) {
        return valueOperations.getAndSet(new RedisKeyDto(userId, chatId), "0");
    }
}
