package com.cxp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.util.Assert;

/**
 * @author : cheng
 * @date : 2020-07-25 16:10
 */
@Configuration
/** @AutoConfigureAfter(RedisAutoConfiguration.class)
 *  是让我们这个配置类在内置的配置类之后在配置，这样就保证我们的配置类生效，并且不会被覆盖配置
 *  */
@AutoConfigureAfter(value = RedisAutoConfiguration.class)
public class RedisConfig {

    @Value("${redis.lock.key}")
    private String redisLockKey;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        Assert.notNull(redisConnectionFactory,"'connectionFactory' cannot be nul");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setDefaultSerializer(serializer);

        return redisTemplate;
    }

    @Bean
    public LockRegistry lockRegistry(LettuceConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, redisLockKey);
    }

}
