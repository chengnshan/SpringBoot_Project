package com.cxp.springquartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = {"com.cxp.springquartz.mapper"})
public class Springboot2QuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2QuartzApplication.class, args);
    }

}
