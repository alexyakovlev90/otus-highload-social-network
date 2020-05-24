package ru.otus.highload.socialbackend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.util.rest.response.ListResponse;
import ru.otus.highload.util.rest.response.Response;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/reg")
    public Response<UserInfoItemDto> createUser(@RequestBody User user) {
        return new Response<>(userService.save(user));
    }

    @PutMapping("/{id}")
    public Response<UserInfoItemDto> updateUser(@RequestBody User user) {
        return new Response<>(userService.save(user));
    }

    @GetMapping
    public ListResponse<UserInfoItemDto> getAllUsers() {
        return new ListResponse<>(userService.getAll());
    }

    @GetMapping("/{id}")
    public Response<UserInfoItemDto> getUser(@PathVariable Long id) {
        return new Response<>(userService.getById(id));
    }

    @GetMapping("/login")
    public Response<UserInfoItemDto> getUserByLogin(@RequestParam("login") String login) {
        return new Response<>(userService.getByLogin(login));
    }

    @GetMapping("/search")
    public ListResponse<UserInfoItemDto> getUser(@RequestParam("firstName") String firstName,
                                                 @RequestParam(value = "lastName", required = false) String lastName) {
        List<UserInfoItemDto> users = userService.searchUser(firstName, lastName);
        return new ListResponse<>(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserInfoItemDto> deleteUser(@PathVariable Long id) {
        return null;
    }
}
