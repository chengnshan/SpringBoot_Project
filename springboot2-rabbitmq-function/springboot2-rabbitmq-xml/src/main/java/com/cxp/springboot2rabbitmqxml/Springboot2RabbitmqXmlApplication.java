package com.cxp.springboot2rabbitmqxml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {"config/rabbitmqConfig.xml"})
public class Springboot2RabbitmqXmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2RabbitmqXmlApplication.class, args);
    }

}
