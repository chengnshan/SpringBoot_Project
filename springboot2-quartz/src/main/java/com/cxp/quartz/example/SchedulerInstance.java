package com.cxp.quartz.example;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author : cheng
 * @date : 2020-03-12 10:59
 */
public class SchedulerInstance {

    public static Scheduler getScheduler() throws SchedulerException, IOException {
        //读取quartz配置文件
        InputStream inputStream = CronTriggerTest.class.getClassLoader()
                .getResourceAsStream("com/cxp/quartz/quartz_custom.properties");
        Properties properties = new Properties();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        properties.load(inputStreamReader);

        SchedulerFactory factory = new StdSchedulerFactory(properties);
        Scheduler myScheduler = factory.getScheduler();

        return myScheduler;
    }
}
