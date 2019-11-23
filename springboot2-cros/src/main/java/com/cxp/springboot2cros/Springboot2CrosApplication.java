package com.cxp.springboot2cros;

import com.cxp.springboot2cros.enablemodule.EnableEnum;
import com.cxp.springboot2cros.enablemodule.EnableHello;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableHello(value = EnableEnum.HELLO2)
@SpringBootApplication
public class Springboot2CrosApplication {

    static {
        System.setProperty("username","user");
    }

    public static void main(String[] args) {
        SpringApplication.run(Springboot2CrosApplication.class, args);
    }

}
