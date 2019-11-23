package com.cxp.springboot2cros.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-13 13:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnSystemPropertyCondition.class)
public @interface ConditionalOnSystemProperty {

    /**java 系统属性名称 */

    String name();
    /** java系统属性值*/
    String value();

}
