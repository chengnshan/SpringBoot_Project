package com.cxp.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : cheng
 * @date : 2020-07-25 16:35
 */
@Configuration
@AutoConfigureAfter(value = RedisAutoConfiguration.class)
public class RedissionConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        StringBuilder sbi = new StringBuilder("redis://");
        sbi.append(redisProperties.getHost()).append(":").append(redisProperties.getPort());
        config.useSingleServer()
                .setAddress(sbi.toString())
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase());

        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;

    }
}
