package com.cxp.quartz.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author : cheng
 * @date : 2020-03-11 12:38
 */
public class SimpleTriggerTest {

    public static void main(String[] args) throws SchedulerException, IOException, InterruptedException {
        Scheduler scheduler = SchedulerInstance.getScheduler();

        JobKey jobKey = new JobKey("MyJobOne","MyJob");
        JobDetail jobDetail = JobBuilder.newJob(MyJobOne.class).withIdentity(jobKey)
                .usingJobData("myJobOne", "MyJobOne123").build();

        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 121000L);
        TriggerKey triggerKey = new TriggerKey("MyJobOneTrigger","MyJobTrigger");
        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
                .startAt(startDate)   //开始执行时间
                .endAt(endDate)     //结束执行时间
                .withIdentity(triggerKey)
                //间隔5秒执行,重复执行
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, simpleTrigger);

        scheduler.start();

        TimeUnit.MINUTES.sleep(30);
        scheduler.shutdown();
    }
}
