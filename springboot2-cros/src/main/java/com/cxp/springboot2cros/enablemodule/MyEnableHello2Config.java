package com.cxp.springboot2cros.enablemodule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-13 11:58
 */
@Slf4j
public class MyEnableHello2Config {

    @Bean
    public String hello2(){
        log.info("Bean {} 注入了", "hello2");
        return "enable hello2 模块";
    }
}
