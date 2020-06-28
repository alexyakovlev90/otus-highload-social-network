package ru.otus.highload.socialbackend.feature.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.feature.wall_post.WallPostService;
import ru.otus.highload.socialbackend.repository.master.UserMasterRepository;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthManager implements AuthenticationManager {

    private static final Logger logger = LoggerFactory.getLogger(AuthManager.class);

    private final UserMasterRepository userMasterRepository;
    private final WallPostService wallPostService;


    @Override
    @Transactional
    public Authentication authenticate(final Authentication auth) throws AuthenticationException {
        if (auth.getPrincipal() == null || auth.getCredentials() == null) {
            throw new RuntimeException("Cannot find login information in authentication given.");
        }

        String login = auth.getPrincipal().toString();
        Optional<User> maybeUser = userMasterRepository.getByLogin(login);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            @NotNull String userPassword = user.getPassword();
            String providedPassword = auth.getCredentials().toString();
            boolean passwordMatch = PasswordUtils.verifyUserPassword(providedPassword, userPassword, PasswordUtils.SALT);

            if (passwordMatch) {
                logger.info("User {} logged in", user.getLogin());
                wallPostService.initUserLenta(user);
                return new UsernamePasswordAuthenticationToken(user, auth.getCredentials(), Collections.emptyList());
            }
        }
        throw new BadCredentialsException("AuthenticatioError.pass.incorrect");

    }
}
