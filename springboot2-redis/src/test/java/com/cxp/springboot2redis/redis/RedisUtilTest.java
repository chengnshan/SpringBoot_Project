package com.cxp.springboot2redis.redis;

import com.cxp.springboot2redis.pojo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/6/23 下午3:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {

    @Autowired
    @Qualifier(value = "redisTemplateObj")
    private RedisTemplate redisTemplateObj;

    @Test
    public void test1(){
    //    System.out.println(RedisUtil.zsetAdd("zset1", "aaa", 1));
        System.out.println(RedisUtil.zsetAdd("zset1", "bbb", 1));

        System.out.println(RedisUtil.zsetRang("zset1",0,-1));
    }

    @Test
    public void test2(){
        redisTemplateObj.opsForValue().set("userInfo1",
                new UserInfo(1,"aaa","bbb","ccc","ddd",new Date()));
        System.out.println(redisTemplateObj.opsForValue().get("userInfo1"));
    }
}