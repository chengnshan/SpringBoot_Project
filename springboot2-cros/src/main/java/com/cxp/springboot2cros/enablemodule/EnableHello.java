package com.cxp.springboot2cros.enablemodule;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-13 12:02
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HelloImportSelector.class)
public @interface EnableHello {

    EnableEnum value() default EnableEnum.HELLO1;
}
