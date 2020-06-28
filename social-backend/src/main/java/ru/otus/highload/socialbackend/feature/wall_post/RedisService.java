package ru.otus.highload.socialbackend.feature.wall_post;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.otus.highload.socialbackend.domain.User;
import ru.otus.highload.socialbackend.domain.WallPost;
import ru.otus.highload.socialbackend.feature.security.SecurityService;

import javax.annotation.Resource;

import java.util.Optional;

import static ru.otus.highload.socialbackend.config.redis.RedisConfig.LENTA_CACHE;

@Log4j2
@Service
@RequiredArgsConstructor
public class RedisService {

    private final WallPostToDtoConverter wallPostToDtoConverter;

    @Qualifier("redisCacheManager")
    @Resource
    private CacheManager cacheManager;

    public void addPostToLenta(Long userId, WallPost wallPost) {
        Cache cache = cacheManager.getCache(LENTA_CACHE);
        Assert.notNull(cache, LENTA_CACHE + " cache is null");

        UserLentaDto userLentaDto = cache.get(userId, UserLentaDto.class);

        if (userLentaDto == null) {
            userLentaDto = new UserLentaDto();
            userLentaDto.setUserId(userId);
        }
        userLentaDto.getWallPosts().add(wallPostToDtoConverter.convert(wallPost));

        cache.put(userId, userLentaDto);
        log.info("Post added to User({}) cache& Post: {}", userId, wallPost);
    }



}
