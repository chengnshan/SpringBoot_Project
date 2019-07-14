package com.cxp.springboot2rsaaes.AES.parameter;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @desc 请求数据解密
 * @author 程
 * @date 2019/7/14 上午9:54
 */

/**
 * @Target说明了Annotation所修饰的对象范围：Annotation可被用于
 * packages、types（类、接口、枚举、Annotation类型）、
 * 类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）
 */
@Target({ElementType.METHOD,ElementType.TYPE})
/**
 * 1、RetentionPolicy.SOURCE：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；
 * 2、RetentionPolicy.CLASS：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期；
 * 3、RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface SecurityParameter {

    /**
     * 入参是否解密，默认解密
     */
    boolean inDecode() default true;

    /**
     * 出参是否加密，默认加密
     */
    boolean outEncode() default true;
}
