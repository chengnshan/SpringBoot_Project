package com.cxp.quartz.example;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author : cheng
 * @date : 2020-03-11 12:38
 */
public class CronTriggerTest {

    public static void main(String[] args) throws SchedulerException, IOException, InterruptedException {
        Scheduler scheduler = SchedulerInstance.getScheduler();

        JobKey jobKey = new JobKey("MyJobCron","MyJob");
        JobDetail jobDetail = JobBuilder.newJob(MyJobOne.class).withIdentity(jobKey).build();

        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 60000L);
        TriggerKey triggerKey = new TriggerKey("MyJobCronTrigger","MyJobTrigger");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
                        .inTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai"))))
                .withIdentity(triggerKey).forJob(jobDetail).startAt(startDate).endAt(endDate).build();

        scheduler.scheduleJob(jobDetail, cronTrigger);

        scheduler.start();

        TimeUnit.SECONDS.sleep(20);

        System.out.println("开始暂停任务");
        scheduler.pauseJob(jobKey);

        TimeUnit.SECONDS.sleep(10);
        System.out.println("开始启动任务");
        scheduler.resumeJob(jobKey);

        TimeUnit.MINUTES.sleep(30);

        scheduler.shutdown();
    }
}
