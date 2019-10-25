package com.cxp.springboot2shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxp.springboot2shardingsphere.mapper.UserMapper;
import com.cxp.springboot2shardingsphere.pojo.User;
import com.cxp.springboot2shardingsphere.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-23 21:29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public List<User> getUserList() {
        return baseMapper.selectList(Wrappers.<User>lambdaQuery());
    }

}
