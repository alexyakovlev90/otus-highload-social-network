package ru.otus.highload.socialbackend.feature.tarantool;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.feature.user.UserInfoItemDto;
import ru.otus.highload.socialbackend.feature.user.UserService;
import ru.otus.highload.util.rest.response.ListResponse;
import ru.otus.highload.util.rest.response.Response;

import java.util.List;
import java.util.stream.Collectors;


//@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class UserSearchController {

    private final UserService userService;
    private final TarantoolService tarantoolService;
    private final UserToUserTarantoolConverter userToUserTarantoolConverter;

    @GetMapping
    public ListResponse<UserTarantoolDto> getUser(@RequestParam("prefix") String prefix, @RequestParam(value = "inMemory", defaultValue = "false") Boolean inMemory) {
        if (inMemory) {
            List<UserTarantoolDto> users = tarantoolService.searchUser(prefix);
            return new ListResponse<>(users);
        }

        List<UserTarantoolDto> users = userService.searchByPrefix(prefix).stream()
                .map(userToUserTarantoolConverter::convert)
                .collect(Collectors.toList());
        return new ListResponse<>(users);
    }
}
