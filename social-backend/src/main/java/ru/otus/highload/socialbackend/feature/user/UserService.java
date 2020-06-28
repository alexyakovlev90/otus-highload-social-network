package ru.otus.highload.socialbackend.feature.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.highload.socialbackend.feature.security.PasswordUtils;
import ru.otus.highload.socialbackend.domain.FriendRequest;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.repository.master.UserMasterRepository;
import ru.otus.highload.socialbackend.repository.slave.FriendRequestSlaveRepository;
import ru.otus.highload.socialbackend.feature.friend_request.FriendRequestService;
import ru.otus.highload.socialbackend.feature.security.SecurityService;
import ru.otus.highload.socialbackend.repository.slave.UserSlaveRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserSlaveRepository userSlaveRepository;
    private final UserMasterRepository userMasterRepository;
    private final UserInfoItemDtoConverter userInfoItemDtoConverter;
    private final FriendRequestService friendRequestService;
    private final SecurityService securityService;
    private final FriendRequestSlaveRepository friendRequestSlaveRepository;

    public UserInfoItemDto getById(Long id) {
        return userMasterRepository.findById(id)
                .map(this::convert)
                .orElse(null);
    }

    public List<UserInfoItemDto> getAll() {
        return userSlaveRepository.findAll().stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserInfoItemDto save(User user) {
        String password = user.getPassword();
        if (password != null) {
            String securePassword = PasswordUtils.generateSecurePassword(password, PasswordUtils.SALT);
            user.setPassword(securePassword);
        }
        final Long isNew = user.getId();
        User savedUser = userMasterRepository.save(user);
        if (isNew == null) {
            user.setRegisterDate(new Date());
            securityService.authenticate(savedUser);
        }
        return this.convert(savedUser);
    }

    public UserInfoItemDto getByLogin(String login) {
        return userMasterRepository.getByLogin(login)
                .map(this::convert)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<UserInfoItemDto> getAuthUserFriends() {
        return securityService.getAuthUser()
                .map(User::getId)
                .map(this::getUserFriends)
                .orElse(Collections.emptyList());
    }

    @Transactional(readOnly = true)
    public List<UserInfoItemDto> getUserFriends(Long userId) {
        List<FriendRequest> userFriends = friendRequestSlaveRepository.getUserFriends(userId);

        List<Long> fromUserIds = userFriends.stream()
                .map(FriendRequest::getUserId)
                .collect(Collectors.toList());

        List<Long> toUserIds = userFriends.stream()
                .map(FriendRequest::getFriendId)
                .collect(Collectors.toList());

        List<UserInfoItemDto> friends = Stream.concat(fromUserIds.stream(), toUserIds.stream())
                .distinct()
                .map(userMasterRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::convert)
                .collect(Collectors.toList());
        friends.removeIf(userInfoItemDto -> userInfoItemDto.getId().equals(userId));
        return friends;
    }

    public UserInfoItemDto convert(User user) {
        UserInfoItemDto itemDto = userInfoItemDtoConverter.convert(user);
        securityService.getAuthUser()
                .map(User::getId)
                .ifPresent(authId -> itemDto.setLoggedIn(authId.equals(user.getId())));
        boolean friend = friendRequestService.isFriend(user.getId());
        return itemDto
                .setFriend(friend);
    }

    public List<User> searchByPrefix(String prefix) {
        String prefixName = capitalize(prefix) +  "%";
        return userSlaveRepository.getByNamePrefix(prefixName);
    }

    public List<UserInfoItemDto> searchUser(String firstName, String lastName) {
        String lastNameLike;
        if (lastName == null) {
            lastNameLike = "%";
        } else {
            lastNameLike = capitalize(lastName) + "$";
        }
        String firstNameLike = capitalize(firstName) +  "%";
        return userSlaveRepository.getByFirstNameAndLastName(firstNameLike, lastNameLike).stream()
                .map(userInfoItemDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
