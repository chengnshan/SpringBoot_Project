package com.cxp.springboot2redis.redission.config;

import java.util.concurrent.TimeUnit;

/**
 * 定义Lock的接口类
 * @author 程
 * @date 2019/7/10 下午9:25
 */
public interface DistributedLocker {

    void lock(String lockKey);

    void unlock(String lockKey);

    void lock(String lockKey, int timeout);

    void lock(String lockKey, TimeUnit unit , int timeout);

    /**
     * 尝试获取锁,
     * @param lockKey
     * @param unit  时间单位
     * @param waitTime  获取锁的时间
     * @param leaseTime 释放锁的时间
     * @return
     */
    boolean tryLock(String lockKey, TimeUnit unit ,long waitTime, long leaseTime);
}
