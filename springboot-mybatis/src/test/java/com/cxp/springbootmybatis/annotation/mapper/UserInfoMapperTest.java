package com.cxp.springbootmybatis.annotation.mapper;

import com.cxp.springbootmybatis.annotation.pojo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/7/8 下午3:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoMapperTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void getUserInfoById() {

        System.out.println(userInfoMapper.getUserInfoById(1));

        UserInfo userInfo = new UserInfo(null,"LICANSHI",null,
                null,null,null);
        System.out.println(userInfoMapper.findUserInfoList(userInfo));

        userInfo = new UserInfo(5,"陈志彪","123456",
                "男","彪哥",new Date());
        userInfo.setJobs("B端组长");
     //   userInfoMapper.insert(userInfo);

    //    userInfoMapper.updateByPrimaryKey(userInfo);

    //    userInfoMapper.insertUserInfo(userInfo);
    }

    @Test
    public void batchInsertUserInfo(){
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(new UserInfo(null,"PENGXU001","123","男","彭旭",new Date(),"B端组员",null));
        userInfos.add(new UserInfo(null,"WANGSHUHANG001","321","男","王顺航",new Date(),"B端组员",null));
        System.out.println(userInfoMapper.batchInsertUserInfo(userInfos));
    }
}