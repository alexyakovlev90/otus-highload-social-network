package ru.otus.highload.socialbackend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialbackend.feature.security.SecurityService;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.rest.response.ListResponse;
import ru.otus.highload.socialbackend.rest.response.Response;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    @PostMapping
    public Response<User> createUser(@RequestBody User user) {
        return new Response<>(userService.save(user));
    }

    @PutMapping("/{id}")
    public Response<User> updateUser(@RequestBody User user) {
        return new Response<>(userService.save(user));
    }

    @GetMapping
    public ListResponse<User> getAllUsers() {
        return new ListResponse<>(userService.getAll());
    }

    @GetMapping("/{id}")
    public Response<User> getUser(@PathVariable Long id) {
        return new Response<>(userService.getById(id));
    }

    @GetMapping("/login")
    public Response<User> getUserByLogin(@RequestParam("login") String login) {
        return new Response<>(userService.getByLogin(login));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return null;
    }
}
