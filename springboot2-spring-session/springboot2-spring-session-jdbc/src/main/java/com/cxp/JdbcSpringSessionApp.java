package com.cxp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Hello world!
 */

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class JdbcSpringSessionApp {
    public static void main(String[] args) {
        SpringApplication.run(JdbcSpringSessionApp.class, args);
    }
}
