package com.cxp.springboot2zookeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  spring 提供的分布式锁使用(zookeeper版)
 *  zookeeper 3.4.x 对应curator 2.12.x系列
 *  zookeeper 3.5.x 对应 curator4.x系列
 */
@SpringBootApplication
public class Springboot2ZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2ZookeeperApplication.class, args);
    }

}
