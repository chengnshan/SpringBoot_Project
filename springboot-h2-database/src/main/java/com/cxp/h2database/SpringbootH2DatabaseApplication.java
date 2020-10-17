package com.cxp.h2database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootH2DatabaseApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringbootH2DatabaseApplication.class);
        application.run(args);
    }

}
