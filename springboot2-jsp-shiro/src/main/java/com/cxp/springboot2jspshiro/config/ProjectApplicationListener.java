package com.cxp.springboot2jspshiro.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-15 22:55
 */
public class ProjectApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
