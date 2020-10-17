package com.cxp.springboot2redis.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 程
 * @date 2019/6/23 下午3:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTestMsg {

    @Value("${mq.queue.first}")
    private String MQ_QUEUE_FIRST;

    @Test
    public void test1() {

    }

}