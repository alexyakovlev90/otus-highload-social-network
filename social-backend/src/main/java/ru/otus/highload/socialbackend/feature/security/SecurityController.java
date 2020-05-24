package ru.otus.highload.socialbackend.feature.security;

import lombok.RequiredArgsConstructor;
import ru.otus.highload.socialbackend.feature.user.UserInfoItemDto;
import ru.otus.highload.socialbackend.feature.user.UserService;
import ru.otus.highload.util.rest.response.Response;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/security")
@Api(value = "security", description = "Some security check methods")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;
    private final UserService userService;

    @GetMapping
    public Response<Void> checkLoggedIn(){
        return new Response<>();
    }

    @GetMapping("/auth")
    public Response<UserInfoItemDto> getAuthUser() {
        UserInfoItemDto authUser = securityService.getAuthUser()
                .map(userService::convert)
                .orElse(null);
//                .orElseGet(() -> userService.getById(1L));
        return new Response<>(authUser);
    }

}
