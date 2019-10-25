package com.cxp.springboot2shardingsphere;

import com.cxp.springboot2shardingsphere.mapper.OrderMapper;
import com.cxp.springboot2shardingsphere.mapper.UserMapper;
import com.cxp.springboot2shardingsphere.pojo.User;
import com.cxp.springboot2shardingsphere.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Springboot2ShardingsphereApplicationTests {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        for (int i = 1; i<=20; i++){
            orderMapper.insertOrder(new BigDecimal(i*10 ),Long.valueOf(i*10),"success");
        }
    }

    @Test
    public void contextLoadsList() {
        List<Long> orderIds = new ArrayList<>();
        orderIds.add(393165004389482496L);
        orderIds.add(393165007073837056L);
        orderIds.add(393165005563887617L);
        orderIds.add(393165006578909185L);
        System.out.println(orderMapper.listByProperty(orderIds));

    }

    @Test
    public void testUserMapper1() {
        for (int i =11 ; i<= 20 ; i++){
            User user = new User();
            user.setId(i);
            user.setAge(12+i-1);
            user.setName(user.getAge()+"name");

            userService.save(user);
        }
    }

    @Test
    public void testUserMapper2() {
        System.out.println(userService.getUserList());
    }
}
