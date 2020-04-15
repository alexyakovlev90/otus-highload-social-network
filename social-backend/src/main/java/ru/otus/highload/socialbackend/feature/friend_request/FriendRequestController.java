package ru.otus.highload.socialbackend.feature.friend_request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.highload.socialbackend.domain.FriendRequest;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.feature.security.SecurityService;
import ru.otus.highload.socialbackend.rest.response.ListResponse;
import ru.otus.highload.socialbackend.rest.response.Response;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friend-requests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;
    private final SecurityService securityService;

    @GetMapping
    public ListResponse<User> getUserFriends(@RequestParam("userId") Long userId) {
        List<User> userFriends;
        if (userId != null) {
            userFriends = friendRequestService.getUserFriends(userId);
        } else {
            userFriends = securityService.getAuthUser()
                    .map(User::getId)
                    .map(friendRequestService::getUserFriends)
                    .orElse(Collections.emptyList());
        }
        return new ListResponse<>(userFriends);
    }

    @PostMapping
    public Response<FriendRequest> addFriend(@RequestParam("fromUserId") Long fromUserId,
                                             @RequestParam("toUserId") Long toUserId,
                                             @RequestParam("userId") Long userId) {
        FriendRequest friendRequest;
        if (userId != null) {
            friendRequest = securityService.getAuthUser()
                    .map(User::getId)
                    .map(authUserId -> friendRequestService.addUserFriend(authUserId, userId))
                    .orElse(null);
        } else {
            friendRequest = friendRequestService.addUserFriend(fromUserId, toUserId);
        }
        return new Response<>(friendRequest);
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
        Boolean isFriend = securityService.getAuthUser()
                .map(user -> friendRequestService.areFriends(user.getId(), userId))
                .orElse(false);
        Response<Boolean> response = new Response<>();
        response.setData(isFriend);
        return response;
    }
}
