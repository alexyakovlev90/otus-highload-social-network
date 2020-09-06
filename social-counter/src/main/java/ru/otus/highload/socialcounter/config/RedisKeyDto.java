package ru.otus.highload.socialcounter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used as chat and messages counter
 *
 * userId, chatId for messages count
 * userId, chatId = null for chats count
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisKeyDto {

    private Long userId;
    private String chatId;

}
