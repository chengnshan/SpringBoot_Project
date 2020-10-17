package com.cxp.springboot2quartz;

import com.cxp.springquartz.pojo.QuartzTaskInfo;
import com.cxp.springquartz.service.QuartzTaskInfoService;
import com.cxp.springquartz.service.QuartzService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot2QuartzApplicationTests {

    @Autowired
    private QuartzService quartzTaskService;

    @Autowired
    private QuartzTaskInfoService quartzTaskInfoService;

    @Autowired
    private Scheduler scheduler;

    @Resource(name = "master")
    private DataSource dataSource1;

    @Resource(name = "quartz")
    private DataSource dataSource2;

    @Test
    public void contextLoads() throws SchedulerException {
        /*QuartzTaskInfo info = new QuartzTaskInfo();
        info.setJobClassName("com.cxp.springboot2quartz.job.MyJob");
        info.setJobName("name");
        info.setJobGroup("group");
        info.setCronExpression("0/1 * * * * ?");
        info.setCreateTime("2020-10-01");
        info.setJobDescription("sssss");
        quartzTaskService.addJob(info);*/

        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
        jobKeys.forEach(jobKey -> {
            System.out.println(jobKey);
        });
    }

    @Test
    public void test1(){
        List<QuartzTaskInfo> list = quartzTaskInfoService.list();
        System.out.println(list);
    }

}
