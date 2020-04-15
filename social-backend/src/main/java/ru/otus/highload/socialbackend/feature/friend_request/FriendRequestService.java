package ru.otus.highload.socialbackend.feature.friend_request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.highload.socialbackend.domain.FriendRequest;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.feature.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    public List<User> getUserFriends(Long userId) {
        List<FriendRequest> userFriends = friendRequestRepository.getUserFriends(userId);

        List<Long> fromUserIds = userFriends.stream()
                .map(FriendRequest::getFromUserId)
                .collect(Collectors.toList());

        List<Long> toUserIds = userFriends.stream()
                .map(FriendRequest::getToUserId)
                .collect(Collectors.toList());

        return Stream.concat(fromUserIds.stream(), toUserIds.stream())
                .distinct()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }

    public FriendRequest addUserFriend(Long from, Long to) {
        FriendRequest friendRequest = friendRequestRepository.getByFromUserIdAndToUserId(from, to);
        if (friendRequest == null) {
            var newFriendRequest = new FriendRequest(from, to);
            return friendRequestRepository.save(newFriendRequest);
        }
        return friendRequest;
    }

    public boolean areFriends(Long from, Long to) {
        return friendRequestRepository.getByFromUserIdAndToUserId(from, to) != null;
    }
}
