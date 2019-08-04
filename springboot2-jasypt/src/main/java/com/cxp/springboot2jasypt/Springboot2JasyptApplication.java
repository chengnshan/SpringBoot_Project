package com.cxp.springboot2jasypt;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EncryptablePropertySource(name = "jasypt",value = "jasypt.properties")
public class Springboot2JasyptApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2JasyptApplication.class, args);
    }

}
