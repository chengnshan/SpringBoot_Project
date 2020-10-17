package com.cxp.springboot2redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class Springboot2RedisClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2RedisClusterApplication.class, args);
    }

}
