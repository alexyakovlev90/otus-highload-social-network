package ru.otus.highload.socialbackend.rest.security;

import ru.otus.highload.socialbackend.rest.response.Response;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для передачи на фронт прав пользователя
 *
 *
 */
@RestController
@RequestMapping(value = "/api/security")
@Api(value = "security", description = "Some security check methods")

public class SecurityController {


    @GetMapping
    public Response checkLoggedIn(){
        return new Response();
    }

}
