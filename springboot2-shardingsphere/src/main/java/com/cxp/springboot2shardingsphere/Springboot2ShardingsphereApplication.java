package com.cxp.springboot2shardingsphere;

import com.cxp.springboot2shardingsphere.config.EnableSharding;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//使用io.shardingsphere时排除io.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration
//使用org.apache.shardingsphere时排除org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration
//@SpringBootApplication(exclude = {org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration.class})
//@SpringBootApplication(exclude = {io.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration.class})
@MapperScan({"com.cxp.springboot2shardingsphere.mapper"})
@SpringBootApplication(exclude = {org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration.class,
        io.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration.class})

@EnableSharding(value = "masterSalveConfig")

public class Springboot2ShardingsphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2ShardingsphereApplication.class, args);
    }

}
