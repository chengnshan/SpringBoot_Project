package com.cxp.springboot2jspshiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxp.springboot2jspshiro.mapper.UserInfoMapper;
import com.cxp.springboot2jspshiro.pojo.UserInfo;
import com.cxp.springboot2jspshiro.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-05 10:46
 */
@Service(value = "userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper,UserInfo> implements UserInfoService {

}
