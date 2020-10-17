package com.cxp.springboot2webmagic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = {"com.cxp.springboot2webmagic.mapper"})
@EnableScheduling
public class Springboot2WebmagicApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2WebmagicApplication.class, args);
    }

}
