package com.cxp.springboot2shiro.pojo;

import lombok.Data;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-05 10:09
 */
@Data
public class UserInfo {

    private Integer id;
    private String userName;
    private String password;
    private String salt;
    private String email;
}
