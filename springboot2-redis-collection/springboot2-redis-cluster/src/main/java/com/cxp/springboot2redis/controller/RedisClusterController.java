package com.cxp.springboot2redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;

/**
 * @author : cheng
 * @date : 2020-08-22 14:43
 */
@RestController
public class RedisClusterController {

    @Autowired
    @Qualifier(value = "redisTemplateJson")
    private RedisTemplate redisTemplateJson;

    @PostConstruct
    public void init(){
        List<RedisClientInfo> clientList = redisTemplateJson.getClientList();
        clientList.stream().forEach(t -> System.out.println(t.getAddressPort()));
        redisTemplateJson.opsForValue().set("redis-cluster","redis-cluster", Duration.ofMinutes(5));
    }

    @RequestMapping(value = "/getRedisClusterData")
    public String getRedisClusterData(){
        return (String) redisTemplateJson.opsForValue().get("redis-cluster");
    }
}
