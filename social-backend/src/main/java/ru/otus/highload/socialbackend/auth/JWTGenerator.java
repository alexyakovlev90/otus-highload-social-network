package ru.otus.highload.socialbackend.auth;

import ru.otus.highload.socialbackend.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class JWTGenerator {

    public String generateToken() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getLogin();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "JWT_KEY")
                .setExpiration(DateUtils.addMinutes(new Date(), 15))
                .addClaims(Collections.singletonMap("login", login))
                .compact();
    }
}
