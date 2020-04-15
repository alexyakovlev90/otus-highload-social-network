package ru.otus.highload.socialbackend.feature.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.User;

import java.util.Optional;

@Service
public class SecurityService {

    public Optional<User> getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User) {
            return Optional.of(auth)
                    .map(Authentication::getPrincipal)
                    .map(User.class::cast);
        }
        return Optional.empty();
    }
}
