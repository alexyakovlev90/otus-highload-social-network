package ru.otus.highload.socialbackend.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.User;

import java.util.Optional;

@Service
public class SecurityService {

    public Optional<User> getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(auth)
                .map(Authentication::getPrincipal)
                .map(User.class::cast);
    }
}
