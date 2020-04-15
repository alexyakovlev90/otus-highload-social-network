package ru.otus.highload.socialbackend.feature.security;

import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.rest.response.Response;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Контроллер для передачи на фронт прав пользователя
 *
 *
 */
@RestController
@RequestMapping(value = "/api/security")
@Api(value = "security", description = "Some security check methods")
public class SecurityController {

    @Resource
    private SecurityService securityService;

    @GetMapping
    public Response<Void> checkLoggedIn(){
        return new Response<>();
    }

    @GetMapping("/auth")
    public Response<User> getAuthUser() {
        User authUser = securityService.getAuthUser()
                .orElse(null);
//                .orElseGet(() -> userService.getById(1L));
        return new Response<>(authUser);
    }

}
