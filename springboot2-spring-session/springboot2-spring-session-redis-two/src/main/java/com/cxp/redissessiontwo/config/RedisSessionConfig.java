package com.cxp.redissessiontwo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.SessionEventHttpSessionListenerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * EnableRedisHttpSession 创建一个名为springSessionRepositoryFilter的Spring Bean，该Bean实现了Filter。
 * 过滤器负责将HttpSession实现替换为由Spring Session支持。在本例中，Spring会话由Redis支持。
 *
 * @author : cheng
 * @date : 2020-03-28 23:18
 */
@EnableRedisHttpSession
@Configuration
@Slf4j
public class RedisSessionConfig implements ApplicationContextAware, BeanClassLoaderAware {

    private ApplicationContext context;
    private ClassLoader loader;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }

    @PostConstruct
    public void init() {
        log.info(Arrays.toString(this.context.getBeanDefinitionNames()));
    }

    //============Redis存储session时使用json序列化================================================
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
        return new GenericJackson2JsonRedisSerializer(objectMapper());
    }

    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
        return mapper;
    }
    //==========================================================================================

    @Bean
    public CookieHttpSessionIdResolver cookieHttpSessionIdResolver(){
        CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName("SESSION");
        defaultCookieSerializer.setCookiePath("/");
        //Cookie失效的时间，单位秒,
        // 1.如果为整数，则该Cookie在maxAge秒后失效。
        // 2.如果为负数，该Cookie为临时Cookie，关闭浏览器即失效，浏览器也不会以任何形式保存该Cookie。
        // 3.如果为0，表示删除该Cookie。默认为-1。
        defaultCookieSerializer.setCookieMaxAge(-1);
        resolver.setCookieSerializer(defaultCookieSerializer);
        return resolver;
    }

    /**
     * session监听
     * @return
     */
    @Bean
    public SessionEventHttpSessionListenerAdapter listenerAdapter(){
        List<HttpSessionListener> listeners = new ArrayList<>(2);
        listeners.add(new CustomSessionListener());
        SessionEventHttpSessionListenerAdapter adapter = new SessionEventHttpSessionListenerAdapter(listeners);
        return adapter;
    }
}
