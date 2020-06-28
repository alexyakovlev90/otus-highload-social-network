package ru.otus.highload.socialbackend.config.rabbit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rabbit")
public class RabbitProp {
    private String username;
    private String password;
    private String host;
    private int port;
}
