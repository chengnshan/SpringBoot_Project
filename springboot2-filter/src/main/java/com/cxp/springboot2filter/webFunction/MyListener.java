package com.cxp.springboot2filter.webFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author 程
 * @date 2019/6/9 下午10:07
 */
@WebListener
public class MyListener implements ServletContextListener {

    private static Logger LOG = LoggerFactory.getLogger(MyListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.info("myListener 初始化...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.info("myListener 销毁...");
    }
}