package com.cxp.springboot2filter.pojo;

import lombok.Data;

/**
 * @author 程
 * @date 2019/6/14 上午9:15
 */

/**
 * 加了@Data注解的类-编译后会自动给我们加上下列方法:
     * 所有属性的get和set方法
     * toString 方法
     * hashCode方法
     * equals方法
 */
@Data
public class UserInfo {

    private Integer id;
    private String userName;
    private String userNo;
    private String address;

}
