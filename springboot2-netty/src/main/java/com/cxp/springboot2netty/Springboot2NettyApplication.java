package com.cxp.springboot2netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Springboot2NettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2NettyApplication.class, args);
    }

}
