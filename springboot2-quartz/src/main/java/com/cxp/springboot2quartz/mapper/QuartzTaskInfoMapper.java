package com.cxp.springboot2quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxp.springboot2quartz.pojo.QuartzTaskInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : cheng
 * @date : 2020-03-10 21:53
 */
public interface QuartzTaskInfoMapper extends BaseMapper<QuartzTaskInfo> {

    List<QuartzTaskInfo> listJobAndTriggerDetails();


}
