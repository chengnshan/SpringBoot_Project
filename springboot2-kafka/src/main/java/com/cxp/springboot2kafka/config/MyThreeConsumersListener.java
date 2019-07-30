package com.cxp.springboot2kafka.config;

import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: SpringBoot_Project
 * @description: @KafkaListener 作为元注释
 *  必须至少为topics，topicPattern或者topicPartitions（和通常，id或者groupId除非您group.id在使用者工厂配置中
 *  指定了）中的一个别名
 * @author: cheng
 * @create: 2019-07-30 20:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@KafkaListener
public @interface MyThreeConsumersListener {

    @AliasFor(annotation = KafkaListener.class, attribute = "id")
    String id();

    @AliasFor(annotation = KafkaListener.class, attribute = "topics")
    String[] topics();

    @AliasFor(annotation = KafkaListener.class, attribute = "concurrency")
    String concurrency() default "3";
}
