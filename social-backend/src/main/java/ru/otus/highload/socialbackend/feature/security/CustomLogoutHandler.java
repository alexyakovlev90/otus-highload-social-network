package ru.otus.highload.socialbackend.feature.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.feature.wall_post.rabbit.RabbitChannelHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomLogoutHandler implements LogoutHandler {


    private final RabbitChannelHolder rabbitChannelHolder;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            // close connection to rabbit / unsubscribe
            User user = (User) authentication.getPrincipal();
            log.info("User {} logged out", user.getLogin());

            rabbitChannelHolder.closeChannel(user);
        }
    }
}
