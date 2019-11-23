package com.cxp.springboot2cros.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-13 13:48
 */
@Slf4j
@Configuration
public class ProfileConfig {

    @Profile("dev")
    @Bean
    public String devStr(){
        log.info("====== devStr ======");
        return "devStr";
    }

    @Profile("test")
    @Bean
    public String testStr(){
        log.info("====== testStr ======");
        return "testStr";
    }
}
