package ru.otus.highload.socialbackend.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redis")
@Getter
@Setter
public class RedisProp {

    private String hostname;
    private int port;
    private int maxActive;
    private int maxIdle;
    private int minIdle;
    private int ttlSeconds;
}
