package com.cxp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  spring 提供的分布式锁使用(redis版),redis分布式锁，内部使用Redission实现
 */
@SpringBootApplication
public class Springboot2IntegrationRedisApp {
    public static void main( String[] args ) {
        SpringApplication.run(Springboot2IntegrationRedisApp.class, args);
    }
}
