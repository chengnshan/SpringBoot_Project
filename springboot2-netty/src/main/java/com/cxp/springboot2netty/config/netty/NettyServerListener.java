package com.cxp.springboot2netty.config.netty;

import com.cxp.springboot2netty.netty.netty_server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-02 20:47
 */
@WebListener
public class NettyServerListener implements ServletContextListener {

    @Autowired
    private NettyServer nettyServer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Thread thread = new Thread(new NettyServerThread());
        // 启动netty服务
        thread.start();
    }

    private class NettyServerThread implements Runnable{
        @Override
        public void run() {
            nettyServer.init();
        }
    }
}
