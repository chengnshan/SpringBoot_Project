package com.cxp.springboot2websocket.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author : cheng
 * @date : 2019-11-02 17:37
 */
@Order(2)
public class MyApplicationContextInitalizer2 implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        System.out.println("MyApplicationContextInitializer2" + environment.getPropertySources().toString());
    }

}
