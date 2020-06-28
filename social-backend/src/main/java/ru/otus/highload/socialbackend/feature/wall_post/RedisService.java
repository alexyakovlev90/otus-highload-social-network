package ru.otus.highload.socialbackend.feature.wall_post;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.otus.highload.socialbackend.domain.WallPost;

import javax.annotation.Resource;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static ru.otus.highload.socialbackend.config.redis.RedisConfig.LENTA_CACHE;

@Log4j2
@Service
@RequiredArgsConstructor
public class RedisService {

    private final WallPostToDtoConverter wallPostToDtoConverter;

    @Lazy
    @Resource
    private WallPostService wallPostService;

    @Qualifier("redisCacheManager")
    @Resource
    private CacheManager cacheManager;

    public void addPostToLenta(Long userId, WallPost wallPost) {
        Cache cache = cacheManager.getCache(LENTA_CACHE);
        Assert.notNull(cache, LENTA_CACHE + " cache is null");
//
//        UserLentaDto userLentaDto = cache.get(userId, UserLentaDto.class);

        UserLentaDto userLentaDto = wallPostService.getLentaCached(userId);

        if (userLentaDto == null) {
            userLentaDto = new UserLentaDto();
            userLentaDto.setUserId(userId);
        }

        List<WallPostDto> wallPosts = userLentaDto.getWallPosts();
        Optional<Long> first = wallPosts.stream()
                .map(WallPostDto::getId)
                .filter(wallPost.getId()::equals)
                .findFirst();
        if (first.isPresent()) {
            log.warn("Post {} already in cache", wallPost);
            return;
        }

        wallPosts.add(wallPostToDtoConverter.convert(wallPost));
        wallPosts.sort((w1, w2) -> w2.getDateCreated().compareTo(w1.getDateCreated()));

        cache.put(userId, userLentaDto);
        log.info("Post added to User({}) cache& Post: {}", userId, wallPost);
    }

    @CacheEvict(cacheNames = LENTA_CACHE, cacheManager = "redisCacheManager")
    public Long evictCache(Long userId) {
        return userId;
    }

}
