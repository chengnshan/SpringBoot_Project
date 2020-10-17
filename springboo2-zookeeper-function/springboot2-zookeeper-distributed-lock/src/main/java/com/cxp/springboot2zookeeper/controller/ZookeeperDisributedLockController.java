package com.cxp.springboot2zookeeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author : cheng
 * @date : 2020-07-22 23:17
 */
@RestController
@Slf4j
public class ZookeeperDisributedLockController {

    @Autowired
    private CuratorFramework curatorFramework;

    @Value(("${lockPath}"))
    private String lockPath ;

    /**
     * 使用carator框架的分布式锁
     * @return
     */
    @RequestMapping(value = "distributedLock")
    public String distributedLock(){
//        log.info("============={} 线程访问开始=========lockName:{}",Thread.currentThread().getName(),lockName);
        //TODO 获取分布式锁
        String lockName = lockPath + UUID.randomUUID().toString();
        InterProcessMutex lock = new InterProcessMutex(curatorFramework,lockPath);
        try{
            //获取锁资源
            boolean flag = lock.acquire(10, TimeUnit.SECONDS);
            if(flag){
                log.info("线程:{}，获取到了锁",Thread.currentThread().getName());
                //TODO 获得锁之后可以进行相应的处理  睡一会
                Thread.sleep(10000);
                log.info("======获得锁后进行相应的操作======" + Thread.currentThread().getName());

                return new String(curatorFramework.getData().forPath(lockName), Charset.defaultCharset());
            }else{
                log.info("线程:{}，超时不获取锁了",Thread.currentThread().getName());
            }
        }catch (Exception e){
            log.info("错误信息：{}",e.getMessage());
        }finally {
            try {
                lock.release();
                log.info("=========lockName:{}==============={}释放了锁",lockName,Thread.currentThread().getName());
            } catch (Exception e) {
                log.info("错误信息：{}",e.getMessage());
            }
        }
        return null;
    }

    /**=================================================================*/
    @Autowired
    private LockRegistry lockRegistry;

    @GetMapping("lock")
    public String lockTest() {
        Lock lock = lockRegistry.obtain("hello");

        lock.lock();
        try {
            log.info("线程:{}，获取到了锁",Thread.currentThread().getName());
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            log.info("=========lockName:==============={}释放了锁",Thread.currentThread().getName());
        }

        return "success";
    }
    /**=================================================================*/
}
