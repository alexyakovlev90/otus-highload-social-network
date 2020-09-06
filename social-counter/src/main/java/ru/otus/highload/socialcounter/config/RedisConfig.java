package ru.otus.highload.socialcounter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
    public RedisTemplate<RedisKeyDto, String> redisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper redisObjectMapper) {
//        new TypeReference<...>() {}
//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        Jackson2JsonRedisSerializer<RedisKeyDto> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(RedisKeyDto.class);
        jackson2JsonRedisSerializer.setObjectMapper(redisObjectMapper);


        RedisTemplate<RedisKeyDto, String> template = new RedisTemplate<>();
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public ValueOperations<RedisKeyDto, String> valueOperations(RedisTemplate<RedisKeyDto, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

}
