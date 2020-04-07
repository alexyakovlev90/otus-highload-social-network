package ru.otus.highload.socialbackend.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class JWTInterceptor implements RequestInterceptor {

    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private JWTGenerator jwtGenerator;

    public JWTInterceptor(JWTGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public void apply(RequestTemplate template) {
        String token = jwtGenerator.generateToken();
        template.header(AUTH_HEADER_NAME, BEARER_PREFIX + token);
    }
}
