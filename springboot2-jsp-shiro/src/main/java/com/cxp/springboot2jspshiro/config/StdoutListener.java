package com.cxp.springboot2jspshiro.config;

import org.apache.commons.logging.impl.LogFactoryImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.PrintStream;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-19 22:42
 */

/**
 * 老的系统中，程序未使用log4j,而是使用System.out.println()将信息打印到了tomcat
 * System.setOut(PrintStream ps)方法允许程序员自行定义System.out输出流,我们可以将我们改造好的PrintStream替换java原来的System.out对象
 */
@WebListener
public class StdoutListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    private void log(Object info) {
        LogFactoryImpl.getLog(getClass()).info(info);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        PrintStream printStream = new PrintStream(System.out) {
            @Override
            public void println(boolean x) {
                log(Boolean.valueOf(x));
            }
            @Override
            public void println(char x) {
                log(Character.valueOf(x));
            }
            @Override
            public void println(char[] x) {
                log(x == null ? null : new String(x));
            }
            @Override
            public void println(double x) {
                log(Double.valueOf(x));
            }
            @Override
            public void println(float x) {
                log(Float.valueOf(x));
            }
            @Override
            public void println(int x) {
                log(Integer.valueOf(x));
            }
            @Override
            public void println(long x) {
                log(x);
            }
            @Override
            public void println(Object x) {
                log(x);
            }
            @Override
            public void println(String x) {
                log(x);
            }
        };
        System.setOut(printStream);
        System.setErr(printStream);
    }

}
