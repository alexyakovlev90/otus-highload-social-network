package ru.otus.highload.socialbackend.feature.friend_request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialbackend.domain.FriendRequest;
import ru.otus.highload.socialbackend.feature.user.UserInfoItemDto;
import ru.otus.highload.socialbackend.feature.user.UserService;
import ru.otus.highload.socialbackend.rest.response.ListResponse;
import ru.otus.highload.socialbackend.rest.response.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend-requests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final UserService userService;

    @GetMapping
    public ListResponse<UserInfoItemDto> getUserFriends(@RequestParam("userId") Long userId) {
        if (userId != null) {
            return new ListResponse<>(userService.getUserFriends(userId));
        } else {
            return new ListResponse<>(userService.getAuthUserFriends());
        }
    }

    @PostMapping
    public Response<FriendRequest> addFriend(@RequestParam("userId") Long userId) {
        return new Response<>(friendRequestService.addFriend(userId));
    }

    @GetMapping("/check")
    public Response<Boolean> checkIfAreFriends(@RequestParam("fromUserId") Long fromUserId, @RequestParam("toUserId") Long toUserId) {
        boolean areFriends = friendRequestService.areFriends(fromUserId, toUserId);
        Response<Boolean> response = new Response<>();
        response.setData(areFriends);
        return response;
    }

    @GetMapping("/is-friend")
    public Response<Boolean> isFriend(@RequestParam("userId") Long userId) {
        Response<Boolean> response = new Response<>();
        response.setData(friendRequestService.isFriend(userId));
        return response;
    }
}
