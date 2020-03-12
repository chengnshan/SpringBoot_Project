package com.cxp.springboot2quartz.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxp.springboot2quartz.config.dynamic.DS;
import com.cxp.springboot2quartz.mapper.QuartzTaskInfoMapper;
import com.cxp.springboot2quartz.pojo.QuartzTaskInfo;
import com.cxp.springboot2quartz.service.QuartzService;
import com.cxp.springboot2quartz.service.QuartzTaskInfoService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : cheng
 * @date : 2020-03-11 16:46
 */
@Service
@DS(value = "MASTER")
public class QuartzTaskInfoServiceImpl extends ServiceImpl<QuartzTaskInfoMapper, QuartzTaskInfo> implements QuartzTaskInfoService {

    private QuartzService quartzService;

    @Autowired
    public void setQuartzService(QuartzService quartzService) {
        this.quartzService = quartzService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveTaskInfo(QuartzTaskInfo taskInfo) throws ClassNotFoundException, SchedulerException {
        boolean save = save(taskInfo);
        if (save) {
            quartzService.addJob(taskInfo);
        }
        return save;
    }

    @Override
    public Page<QuartzTaskInfo> listTaskByCondition(QuartzTaskInfo taskInfo, Integer pageNum, Integer pageSize) {
        Page<QuartzTaskInfo> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QuartzTaskInfo> wrapper = new QueryWrapper<QuartzTaskInfo>();
        if (!StringUtils.isEmpty(taskInfo.getJobName())){
            wrapper.eq("JOB_NAME", taskInfo.getJobName());
        }
        if (!StringUtils.isEmpty(taskInfo.getJobGroup())){
            wrapper.eq("JOB_GROUP", taskInfo.getJobGroup());
        }
        if (!StringUtils.isEmpty(taskInfo.getJobClassName())){
            wrapper.eq("JOB_CLASS_NAME", taskInfo.getJobClassName());
        }
        Page<QuartzTaskInfo> pages = page(page, wrapper);
        return pages;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean pauseJob(QuartzTaskInfo info) throws SchedulerException {
        //更新任务状态
        info.setJobStatus("0");
        boolean b = this.updateById(info);
        if (!b){
            throw new RuntimeException("更新失败!");
        }
        //停止任务
        quartzService.pause(info);
        return b;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean resumeJob(QuartzTaskInfo info) throws SchedulerException {
        //更新任务状态
        info.setJobStatus("1");
        boolean b = this.updateById(info);
        if (!b){
            throw new RuntimeException("更新失败!");
        }
        //停止任务
        quartzService.resume(info);
        return b;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteJob(QuartzTaskInfo info) throws SchedulerException {
        quartzService.delete(info);
        boolean b = this.removeById(info.getId());
        return b;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateJob(QuartzTaskInfo info) throws SchedulerException {
        boolean b = this.updateById(info);
        if (!b){
            throw new RuntimeException("更新失败!");
        }
        //更新定时任务cron表达式
        quartzService.edit(info);
        return b;
    }

}
