package com.cxp.quartz.example;

import org.quartz.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : cheng
 * @date : 2020-03-12 10:59
 */
public class CalendarIntervalTriggerTest {

    public static void main(String[] args) throws IOException, SchedulerException, InterruptedException {
        Scheduler scheduler = SchedulerInstance.getScheduler();

        JobKey jobKey = new JobKey("MyJobCron","MyJob");
        JobDetail jobDetail = JobBuilder.newJob(MyJobOne.class).withIdentity(jobKey).build();

        CalendarIntervalTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                .withIntervalInDays(1)).withIdentity("trigger", "trigger")
                .startNow().forJob(jobDetail).build();

        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();

        TimeUnit.MINUTES.sleep(30);
        scheduler.shutdown();
    }
}
