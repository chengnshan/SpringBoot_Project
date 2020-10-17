package com.cxp.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author : cheng
 * @date : 2020-07-25 16:18
 */
@RestController
@Slf4j
public class RedisDistributedLock {

    @Value("${registry.lock.key}")
    private String registryLockKey;

    @Value("${redisson.lock.key}")
    private String redissonLockKey;

    private LockRegistry lockRegistry;

    private RedissonClient redissonClient;

    @Autowired
    public void setLockRegistry(LockRegistry lockRegistry) {
        this.lockRegistry = lockRegistry;
    }

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 使用Spring集成的redis分布式锁
     * @return
     */
    @GetMapping(value = "/getDistributedLock")
    public String getDistributedLock(){
        Lock lock = lockRegistry.obtain(registryLockKey);
        lock.lock();
        try {
            log.info("线程:{}，获取到了锁",Thread.currentThread().getName());
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
            log.info("=========lockName:==============={}释放了锁",Thread.currentThread().getName());
        }
        return "success";
    }

    /**
     * redisson实现的分布式锁
     * @return
     */
    @RequestMapping(value = "/getRedissonDirtributedLock")
    public String getRedissonDirtributedLock(){
        RLock lock = redissonClient.getLock(redissonLockKey);
        try {
            lock.lock(65L, TimeUnit.SECONDS);
            Thread.sleep(5000);
            log.info("线程:{}，获取到了锁",Thread.currentThread().getName());
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }finally {
            lock.unlock();
            log.info("=========lockName:==============={}释放了锁",Thread.currentThread().getName());
        }
        return "";
    }
}
