package com.cxp.springboot2webmagic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxp.springboot2webmagic.mapper.JobInfoMapper;
import com.cxp.springboot2webmagic.pojo.JobInfo;
import com.cxp.springboot2webmagic.service.JobInfoService;
import org.springframework.stereotype.Service;

/**
 * @author : cheng
 * @date : 2019-11-21 13:36
 */
@Service
public class JobInfoServiceImpl extends ServiceImpl<JobInfoMapper, JobInfo> implements JobInfoService {
}
