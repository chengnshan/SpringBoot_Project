package com.cxp.springbootmybatis.annotation.mapper;

import com.cxp.springbootmybatis.annotation.pojo.RoleInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/7/8 下午12:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleInfoMapperTest {

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Autowired
    private UserRoleInfoMapper userRoleInfoMapper;

    @Test
    public void testFindRoleInfoList(){
        System.out.println(roleInfoMapper.findRoleInfoList(new RoleInfo(null,"technical_director",null)));

        /*RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId("technical_director");
        roleInfo.setRoleName("技术总监");
        roleInfoMapper.insert(roleInfo);*/

        System.out.println(roleInfoMapper.selectAll());
    }
}