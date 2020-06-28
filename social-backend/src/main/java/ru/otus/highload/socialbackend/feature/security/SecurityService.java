package ru.otus.highload.socialbackend.feature.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.User;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

//    private final AuthManager authManager;

    public Optional<User> getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User) {
            return Optional.of(auth)
                    .map(Authentication::getPrincipal)
                    .map(User.class::cast);
        }
        return Optional.empty();
    }

    public void authenticate(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, user.getPassword(), Collections.emptyList());
//        authManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
