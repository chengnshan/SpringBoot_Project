package com.cxp.springboot2mybatisplus.mybatisplus1.pojo;

import lombok.Data;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-02 17:27
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
