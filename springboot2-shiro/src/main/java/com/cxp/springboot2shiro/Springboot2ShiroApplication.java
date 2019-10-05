package com.cxp.springboot2shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cxp.springboot2shiro.mapper")
public class Springboot2ShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2ShiroApplication.class, args);
    }

}
