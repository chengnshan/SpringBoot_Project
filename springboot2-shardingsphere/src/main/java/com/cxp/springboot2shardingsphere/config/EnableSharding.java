package com.cxp.springboot2shardingsphere.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author : cheng
 * @date : 2019-10-27 15:46
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ShardingSelector.class)
public @interface EnableSharding {

    String value() default "dataShardingConfig";
}
