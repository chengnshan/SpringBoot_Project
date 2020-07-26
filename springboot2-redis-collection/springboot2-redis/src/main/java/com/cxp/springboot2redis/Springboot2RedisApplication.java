package com.cxp.springboot2redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Springboot2RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2RedisApplication.class, args);
    }

}
