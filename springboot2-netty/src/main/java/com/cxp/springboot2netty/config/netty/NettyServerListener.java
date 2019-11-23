package com.cxp.springboot2netty.config.netty;

import com.cxp.springboot2netty.netty.netty_server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ServletContextListener 接口作用：监听 ServletContext 对象的生命周期，实际上就是监听 Web 应用的生命周期
 * ServletContextListener 接口主要有两个方法，一个在当Servlet 容器启动web应用时调用，另一个是在Servlet 容器终止web应用时调用。
 * 实现 ServletContextListener 接口需重写这两方法。
 *
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
