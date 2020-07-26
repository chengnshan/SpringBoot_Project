package com.cxp.springboot2zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot2ZookeeperApplicationTests {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void contextLoads() throws Exception {
        byte[] data1 = curatorFramework.getData().forPath("/study");
        System.out.println(new String(data1));
    }

}
