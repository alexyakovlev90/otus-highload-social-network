package ru.otus.highload.socialbackend.feature.friend_request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.highload.socialbackend.domain.FriendRequest;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.feature.security.SecurityService;
import ru.otus.highload.socialbackend.repository.master.FriendRequestMasterRepository;
import ru.otus.highload.socialbackend.repository.slave.FriendRequestSlaveRepository;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestSlaveRepository friendRequestSlaveRepository;
    private final FriendRequestMasterRepository friendRequestMasterRepository;
    private final SecurityService securityService;

    @Transactional
    public FriendRequest addUserFriend(Long from, Long to) {
        FriendRequest friendRequest = friendRequestSlaveRepository.getByFromUserIdAndToUserId(from, to);
        if (friendRequest == null) {
            var newFriendRequest = new FriendRequest(from, to);
            return friendRequestMasterRepository.save(newFriendRequest);
        }
        return friendRequest;
    }

    @Transactional
    public FriendRequest addFriend(Long userId) {
        return securityService.getAuthUser()
                .map(User::getId)
                .map(authUserId -> this.addUserFriend(authUserId, userId))
                .orElse(null);
    }

    public boolean areFriends(Long from, Long to) {
        return friendRequestSlaveRepository.getByFromUserIdAndToUserId(from, to) != null;
    }

    public boolean isFriend(Long userId) {
        return securityService.getAuthUser()
                .map(user -> this.areFriends(user.getId(), userId))
                .orElse(false);
    }
}
