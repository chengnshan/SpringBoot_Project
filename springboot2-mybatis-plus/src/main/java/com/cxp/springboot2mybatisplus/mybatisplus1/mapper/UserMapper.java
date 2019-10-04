package com.cxp.springboot2mybatisplus.mybatisplus1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxp.springboot2mybatisplus.mybatisplus1.pojo.User;

import java.util.List;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-02 17:29
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> listByProperty(User user);
}
