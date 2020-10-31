package com.cxp.springboot2rabbitmq.mapper;

import com.cxp.springboot2rabbitmq.rabbitConsumer.pojo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 程
 * @date 2019/7/3 上午11:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoMapperTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void findUserInfoList() {
        UserInfo userInfo = new UserInfo();
        //userInfo.setUserName("李灿世");
        userInfo.setId(1);
        List<UserInfo> userInfoList = userInfoMapper.findUserInfoList(userInfo);
        System.out.println("结果： "+userInfoList);
    }

    @Test
    public void getUserInfoById() {
        System.out.println(userInfoMapper.getUserInfoById(1));
    }
}