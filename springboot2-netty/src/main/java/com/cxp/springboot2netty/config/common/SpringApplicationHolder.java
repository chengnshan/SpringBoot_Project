package com.cxp.springboot2netty.config.common;

import io.netty.channel.Channel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-04 21:30
 */
@Component
public class SpringApplicationHolder implements ApplicationContextAware {

    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationHolder.applicationContext = applicationContext;
    }

    /**
     *
     * @param beanName  spring配置文件中配置的bean名或注解的名称
     * @param <T>
     * @return  一个以所给名字注册的bean的实例
     * @throws BeansException 抛出spring异常
     */
    @SuppressWarnings("unchecked")
    public static <T>  T getBeanByName(String beanName) throws BeansException{
        return (T)applicationContext.getBean(beanName);
    }

    /**
     *
     * @param clazz 需要获取的bean的类型
     * @param <T>
     * @return  该类型的一个在ioc容器中的bean
     * @throws BeansException 抛出spring异常
     */
    public static <T>  T getBeanByClass(Class<T> clazz) throws BeansException{
        return applicationContext.getBean(clazz);
    }

    /**
     * 如果ioc容器中包含一个与所给名称匹配的bean定义，则返回true否则返回false
     *
     * @param name ioc容器中注册的bean名称
     * @return 存在返回true否则返回false
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

}
