package com.cxp.quartz.example;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : cheng
 * @date : 2020-03-11 12:36
 */
public class MyJobOne implements Job {

    private String myJobOne;

    public String getMyJobOne() {
        return myJobOne;
    }

    public void setMyJobOne(String myJobOne) {
        this.myJobOne = myJobOne;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(String.format("%s , 开始运行: %s, 参数: %s .",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                this.getClass().getName(),
                this.getMyJobOne()));
    }
}
