package ru.otus.highload.socialbackend.config;

import ru.otus.highload.socialbackend.auth.AdminAuthManager;
import ru.otus.highload.socialbackend.rest.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private AdminAuthManager adminAuthManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/logout").permitAll()
                //swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/documentation/swagger/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
//                .anyRequest().authenticated();
                .anyRequest().permitAll();

        http
                .cors()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/api/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .successHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().print(objectMapper.writeValueAsString(new Response<Void>(true)));
                })
                .failureHandler((request, response, exception) -> {
                    if (Objects.nonNull(exception)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().print(objectMapper.writeValueAsString(new Response<Void>(exception)));
                    }
                })
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().print(objectMapper.writeValueAsString(new Response<Void>(true)));
                });

        http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, exception) -> {
                    if (Objects.nonNull(exception)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().print(objectMapper.writeValueAsString(new Response<Void>(exception)));
                    }
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(adminAuthManager);
    }

}
