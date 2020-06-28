package ru.otus.highload.socialbackend.config.rabbit;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory(RabbitProp rabbitProp) {
        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitProp.getHost());
        connectionFactory.setPort(rabbitProp.getPort());
        connectionFactory.setUsername(rabbitProp.getUsername());
        connectionFactory.setPassword(rabbitProp.getPassword());
        return connectionFactory;
    }
}
