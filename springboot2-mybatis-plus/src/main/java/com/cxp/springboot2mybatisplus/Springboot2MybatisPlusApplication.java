package com.cxp.springboot2mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cxp.springboot2mybatisplus.*.mapper")
public class Springboot2MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2MybatisPlusApplication.class, args);
    }

}
