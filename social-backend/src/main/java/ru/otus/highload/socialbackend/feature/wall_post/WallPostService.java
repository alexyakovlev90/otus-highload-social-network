package ru.otus.highload.socialbackend.feature.wall_post;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.domain.WallPost;
import ru.otus.highload.socialbackend.feature.security.SecurityService;
import ru.otus.highload.socialbackend.feature.wall_post.rabbit.RabbitService;
import ru.otus.highload.socialbackend.repository.master.WallPostRepository;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.highload.socialbackend.config.redis.RedisConfig.LENTA_CACHE;

@Log4j2
@Service
@RequiredArgsConstructor
public class WallPostService {

    private final RabbitService rabbitService;
    private final WallPostRepository wallPostRepository;
    private final WallPostToDtoConverter wallPostToDtoConverter;
    private final SecurityService securityService;

    @Resource
    private WallPostService self;

    public void initUserLenta(User user) {
        // warm up lenta cache and subscribe to new posts
        self.getLentaCached(user.getId());
        rabbitService.subscribeToFriendsPosts(user);
    }

    @Transactional
    public WallPostDto savePost(WallPost wallPost) {
        wallPost.setDateCreated(LocalDateTime.now());
        Optional<User> authUser = securityService.getAuthUser();
        Assert.isTrue(authUser.isPresent(), "Auth user must be presented");
        wallPost.setFromUser(authUser.get().getId());

        WallPost save = wallPostRepository.save(wallPost);
        rabbitService.publish(save);
        return wallPostToDtoConverter.convert(save);
    }

    public List<WallPostDto> getUserWallPosts(Long userId) {
        if (userId != null) {
            return wallPostRepository.getWallPostsByToUserOrderByDateCreatedDesc(userId).stream()
                    .map(wallPostToDtoConverter::convert)
                    .collect(Collectors.toList());
        }

        return securityService.getAuthUser()
                .map(User::getId)
                .map(wallPostRepository::getWallPostsByToUserOrderByDateCreatedDesc)
                .orElse(Collections.emptyList()).stream()
                .map(wallPostToDtoConverter::convert)
                .collect(Collectors.toList());

    }

    public List<WallPostDto> getUserLentaPosts(Long userId) {
        if (userId != null) {
            return self.getLentaCached(userId).getWallPosts();
        }

        return securityService.getAuthUser()
                .map(User::getId)
                .map(self::getLentaCached)
                .map(UserLentaDto::getWallPosts)
                .orElse(Collections.emptyList());
    }

    @Cacheable(cacheManager = "redisCacheManager", cacheNames = LENTA_CACHE, key = "#userId")
    public UserLentaDto getLentaCached(Long userId) {
        log.info("put lenta to cache for user {}", userId);
        List<WallPostDto> userLentaPosts = getUserLentaPosts(userId, 1L, 1000L).stream()
                .map(wallPostToDtoConverter::convert)
                .collect(Collectors.toList());
        return new UserLentaDto()
                .setUserId(userId)
                .setWallPosts(userLentaPosts);
    }


    public List<WallPost> getUserLentaPosts(Long userId, Long minId, Long limit) {
        return wallPostRepository.getByToUserInWithOffset(userId, minId, limit);
    }
}
