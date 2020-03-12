package com.cxp.quartz.example;

import org.quartz.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * @author : cheng
 * @date : 2020-03-12 12:25
 */
public class DailyTimeIntervalTriggerTest {
    public static void main(String[] args) throws IOException, SchedulerException, InterruptedException {
        Scheduler scheduler = SchedulerInstance.getScheduler();

        JobKey jobKey = new JobKey("MyJobCron", "MyJob");
        JobDetail jobDetail = JobBuilder.newJob(MyJobOne.class).withIdentity(jobKey).build();

        DailyTimeIntervalTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(
                        DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(9, 0)) //每天9：00开始
                                .endingDailyAt(TimeOfDay.hourAndMinuteOfDay(16, 0)) //16：00 结束
                                .onDaysOfTheWeek(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY) //周一至周五执行
                                .withIntervalInHours(1) //每间隔1小时执行一次
                                .withRepeatCount(100)) //最多重复100次（实际执行100+1次）
                .withIdentity("trigger", "trigger").forJob(jobDetail)
                .startNow().build();

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();

        TimeUnit.MINUTES.sleep(30);
        scheduler.shutdown();

    }
}
