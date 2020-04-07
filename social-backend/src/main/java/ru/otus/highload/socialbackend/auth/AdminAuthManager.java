package ru.otus.highload.socialbackend.auth;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("adminAuthManager")
public class AdminAuthManager implements AuthenticationManager {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    @Transactional
    public Authentication authenticate(final Authentication auth) throws AuthenticationException {
        if (auth.getPrincipal() == null || auth.getCredentials() == null) {
            throw new RuntimeException("Cannot find login information in authentication given.");
        }
        String login = auth.getPrincipal().toString();
        if ("user".equals(login)) {
            return new UsernamePasswordAuthenticationToken("user", "user");
        } else {
            throw new BadCredentialsException("AuthenticatioError.pass.incorrect");
        }
    }

}
