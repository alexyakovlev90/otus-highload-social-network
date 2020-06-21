package ru.otus.highload.socialbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.highload.socialbackend.feature.clickhouse.KittenhouseApiClient;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    private final SocialProps socialProps;

    @Bean
    public KittenhouseApiClient bestClientOfferClient(ObjectMapper objectMapper) {
        return Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new Encoder.Default())
                .decoder(new JacksonDecoder(objectMapper))
                .target(KittenhouseApiClient.class, socialProps.getKittenhouseUrl());
    }
}
