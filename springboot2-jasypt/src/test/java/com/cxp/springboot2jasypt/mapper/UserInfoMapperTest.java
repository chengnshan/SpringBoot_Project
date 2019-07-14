package com.cxp.springboot2jasypt.mapper;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/7/13 下午6:28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInfoMapperTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    @Qualifier(value = "pooledPBEStringEncryptor")
    private StringEncryptor stringEncryptor;

    @Test
    public void test1(){
        System.out.println(stringEncryptor);
        System.out.println(stringEncryptor.encrypt("root"));
        System.out.println(userInfoMapper.getUserInfoById(1));
    }
}