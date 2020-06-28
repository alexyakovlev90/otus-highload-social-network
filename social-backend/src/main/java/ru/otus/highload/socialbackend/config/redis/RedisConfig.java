package ru.otus.highload.socialbackend.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.otus.highload.socialbackend.feature.wall_post.UserLentaDto;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Pool config:
 * https://www.bytepitch.com/blog/redis-integration-spring-boot/
 *
 * Cache config:
 * https://programmerfriend.com/ultimate-guide-to-redis-cache-with-spring-boot-2-and-spring-data-redis/
 */
@EnableCaching
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    public static final String LENTA_CACHE = "lentaCache";

    private final RedisProp redisProp;

    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        return new RedisStandaloneConfiguration(redisProp.getHostname(), redisProp.getPort());
    }

    @Bean
    public ClientOptions clientOptions() {
        return ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .autoReconnect(true)
                .build();
    }

    @Bean
    LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options, ClientResources clientResources){
        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(redisProp.getMaxActive());
        poolConfig.setMaxTotal(redisProp.getMaxIdle());
        poolConfig.setMaxIdle(redisProp.getMinIdle());

        return LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .clientOptions(options)
                .clientResources(clientResources)
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration,
                                                    LettucePoolingClientConfiguration lettucePoolConfig) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
    }

    @Bean(name = "redisObjectMapper")
    public ObjectMapper redisObjectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    @Primary
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper redisObjectMapper) {
//        new TypeReference<...>() {}
        Jackson2JsonRedisSerializer<UserLentaDto> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(UserLentaDto.class);
        jackson2JsonRedisSerializer.setObjectMapper(redisObjectMapper);

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }


    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(ObjectMapper redisObjectMapper) {
        Jackson2JsonRedisSerializer<UserLentaDto> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(UserLentaDto.class);
        jackson2JsonRedisSerializer.setObjectMapper(redisObjectMapper);
        RedisSerializationContext.SerializationPair<UserLentaDto> jsonSerializer =
                RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer);

        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(jsonSerializer)
                .entryTtl(Duration.ofSeconds(redisProp.getTtlSeconds()));
    }

    @Bean("redisCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheConfiguration redisCacheConfiguration) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put(LENTA_CACHE, redisCacheConfiguration);

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }


}
