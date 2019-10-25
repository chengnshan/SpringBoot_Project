package com.cxp.springboot2shardingsphere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxp.springboot2shardingsphere.pojo.User;

import java.util.List;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-23 21:29
 */
public interface UserService extends IService<User> {

    /**
     * 保存用户信息
     * @param entity
     * @return
     */
    @Override
    boolean save(User entity);

    /**
     * 查询全部用户信息
     * @return
     */
    List<User> getUserList();

}
