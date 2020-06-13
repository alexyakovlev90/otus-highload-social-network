package ru.otus.highload.socialbackend.config.data.tarantool;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource-tarantool")
@Getter
@Setter
public class TarantoolProp {

    private String jdbcUrl;
    private String host;
    private String username;
    private String password;
}
