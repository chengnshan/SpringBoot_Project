package com.cxp.springboot2webmagic.config;

import com.cxp.springboot2webmagic.pojo.JobInfo;
import com.cxp.springboot2webmagic.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author : cheng
 * @date : 2019-11-22 16:12
 */
@Component
public class PersistencePipeline implements Pipeline {

    @Autowired
    private JobInfoService jobInfoService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取
        JobInfo jobInfo = resultItems.get("jobInfo");
        //判断数据是否不为空
        if (jobInfo != null){
            this.jobInfoService.save(jobInfo);
        }
    }
}
