package com.cxp.springboot2zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author 程
 * @date 2019/7/29 下午3:40
 */
public class CuratorBase {

    /** zookeeper地址 */
    static final String CONNECT_ADDR = "10.211.55.5:2181";
    static final String CONNECT_ADDR_CLUSTER = "10.211.55.5:2182,10.211.55.5:2183,10.211.55.5:2184";
    /** session超时时间  ms */
    static final int SESSION_OUTTIME = 5000;

    public static void main(String[] args) throws Exception {
        //1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 2);
        //2 通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_ADDR_CLUSTER)
                .sessionTimeoutMs(SESSION_OUTTIME)
                .retryPolicy(retryPolicy)
//					.namespace("super")
                .build();
        //3 开启连接
        cf.start();
        System.out.println(cf.getState());

        Charset charset = Charset.forName("utf-8");
    //    String forPath = cf.create().forPath("/study", "学习Zookeeper".getBytes(charset));
    //    System.out.println(forPath);
        byte[] path = cf.getData().forPath("/study");
        String s = new String(path, charset);
        System.out.println(s);

        cf.close();
    }

    private void connectSingle() throws Exception{
        //1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 2);
        //2 通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_ADDR)
                .sessionTimeoutMs(SESSION_OUTTIME)
                .retryPolicy(retryPolicy)
//					.namespace("super")
                .build();
        //3 开启连接
        cf.start();

        System.out.println(ZooKeeper.States.CONNECTED);
        System.out.println(cf.getState());

        // 新加、删除

        //4 建立节点 指定节点类型（不加withMode默认为持久类型节点）、路径、数据内容
        String result1 = cf.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/zookeeper/super/c1","c1内容".getBytes());
        System.out.println(result1);
        //5 删除节点
        cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super/c2");
        cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/");

        cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/super/c2","c2内容".getBytes());
        Stat stat = cf.checkExists().forPath("/super/c2");
        System.out.println(stat.toString());
        //读取节点
        String ret1 = new String(cf.getData().forPath("/super/c2"));
        System.out.println(ret1);
        //修改节点
        cf.setData().forPath("/super/c2", "修改c2内容c2内容c2内容".getBytes());
        String ret2 = new String(cf.getData().forPath("/super/c2"));
        System.out.println(ret2);

        GetChildrenBuilder children = cf.getChildren();
        List<String> strings = children.forPath("/kafka");
        System.out.println(strings);

        cf.close();
    }
}
