package com.cxp.springboot2zookeeper.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author 程
 * @date 2019/7/29 下午3:34
 */
@Configuration
public class CuratorFrameworkConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CuratorFrameworkConfig.class);

    @Value("${zk.url:10.211.55.6:2181}")
    private String zkUrl;

    @Value("${zk.cluster.url}")
    private String zkClusterUrl;

    @Value("${zk.sessionTimeoutMs:500}")
    private int sessionTimeoutMs;

    @Value("${zk.connectionTimeoutMs:500}")
    private int connectionTimeoutMs;

    @Value(("${lockPath}"))
    private String lockPath ;

    /**
     * 使用spring integration与zookeeper结合，实现zookeeper分布式锁
     * @param curatorFramework Curator连接客户端
     * @return
     */
    @Bean
    public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework) {
        return new ZookeeperLockRegistry(curatorFramework, lockPath);
    }

    @Bean
    public CuratorFramework getCuratorFramework(){
/*
        // ExponentialBackoffRetry是种重连策略，每次重连的间隔会越来越长,1000毫秒是初始化的间隔时间,3代表尝试重连次数。
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 创建client
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkClusterUrl, retryPolicy);
        // 添加watched 监听器
        client.getCuratorListenable().addListener(new MyCuratorListener());
        client.start();
*/
        CuratorFrameworkConfig.CuratorFrameworkBean curatorFrameworkBean = new CuratorFrameworkConfig.CuratorFrameworkBean(zkClusterUrl);
        CuratorFramework curatorFramework = null;
        try {
            curatorFramework = curatorFrameworkBean.getObject();
        } catch (Exception e) {
            LOGGER.error("getCuratorFramework getObject exception: "+e.getMessage(),e);
        }
        return curatorFramework;
    }

     class CuratorFrameworkBean implements FactoryBean<CuratorFramework>,InitializingBean,DisposableBean {

        private String connectionString;
        private int sessionTimeoutMs;
        private int connectionTimeoutMs;
        private RetryPolicy retryPolicy;
        private CuratorFramework client;

        private CuratorFrameworkBean(String connectionString) {
            this(connectionString, CuratorFrameworkConfig.this.sessionTimeoutMs, CuratorFrameworkConfig.this.connectionTimeoutMs);
        }

        private CuratorFrameworkBean(String connectionString, int sessionTimeoutMs, int connectionTimeoutMs) {
            this.connectionString = connectionString;
            this.sessionTimeoutMs = sessionTimeoutMs;
            this.connectionTimeoutMs = connectionTimeoutMs;
            try {
                this.afterPropertiesSet();
            } catch (Exception e) {
                LOGGER.error("getCuratorFramework create CuratorFramework exception: "+e.getMessage(),e);
            }
        }

        @Override
        public CuratorFramework getObject() throws Exception {
            return this.client;
        }

        @Override
        public Class<?> getObjectType() {
            return this.client != null ? this.client.getClass() : CuratorFramework.class;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            if (StringUtils.isEmpty(this.connectionString)) {
                throw new IllegalStateException("connectionString can not be empty.");
            } else {
                if (this.retryPolicy == null) {
                    // ExponentialBackoffRetry是种重连策略，每次重连的间隔会越来越长,1000毫秒是初始化的间隔时间,3代表尝试重连次数。
                    this.retryPolicy = new ExponentialBackoffRetry(1000, 3, 3000);
                }
            }
            // 创建client
            client = CuratorFrameworkFactory.newClient(this.connectionString,this.sessionTimeoutMs,
                    this.connectionTimeoutMs, retryPolicy);
            // 添加watched 监听器
            client.getCuratorListenable().addListener(new MyCuratorListener());
            client.start();
        }

        @Override
        public void destroy() throws Exception {
            LOGGER.info("Closing curator framework...");
            client.close();
            LOGGER.info("Closed curator framework.");
        }
    }

     static class MyCuratorListener implements CuratorListener {
        @Override
        public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
            CuratorEventType type = event.getType();
            if(type == CuratorEventType.WATCHED){
                WatchedEvent watchedEvent = event.getWatchedEvent();
                String path = watchedEvent.getPath();
                System.out.println(watchedEvent.getType()+" -- "+ path);
                // 重新设置改节点监听
                if(null != path){
                    client.checkExists().watched().forPath(path);
                }
            }
        }
    }
}
