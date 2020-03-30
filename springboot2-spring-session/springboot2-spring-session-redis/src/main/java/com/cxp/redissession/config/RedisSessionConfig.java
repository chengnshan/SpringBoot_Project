package com.cxp.redissession.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * EnableRedisHttpSession 创建一个名为springSessionRepositoryFilter的Spring Bean，该Bean实现了Filter。
 * 过滤器负责将HttpSession实现替换为由Spring Session支持。在本例中，Spring会话由Redis支持。
 *
 * @author : cheng
 * @date : 2020-03-28 23:18
 */
@EnableRedisHttpSession
@Configuration
public class RedisSessionConfig implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    @PostConstruct
    public void init() {
        System.out.println(Arrays.toString(this.context.getBeanDefinitionNames()));
    }

}
