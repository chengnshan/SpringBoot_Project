package com.cxp.springboot2cros.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-13 13:59
 */
@Configuration
@Slf4j
@ConditionalOnSystemProperty(name = "username", value = "user")
public class ConditionConfig {

    @Bean
    public String conditionStr(){
        log.info("====== conditionStr ======");
        return "=== conditionStr ===";
    }
}
