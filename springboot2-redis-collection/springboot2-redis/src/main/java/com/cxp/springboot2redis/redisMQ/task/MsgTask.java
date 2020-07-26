package com.cxp.springboot2redis.redisMQ.task;

import com.alibaba.fastjson.JSONObject;
import com.cxp.springboot2redis.redisMQ.config.RedisMQ;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 程
 * @date 2019/7/10 下午4:21
 */
@Component
public class MsgTask {

    @Resource
    private RedisMQ redisMQ;

    @Value("${mq.consumer.first}")
    private String MQ_LIST_FIRST;

    /**
     * 监听消息队列1
     */
    @Scheduled(cron="*/5 * * * * *")
    public void listenerList1() {
        // 消费
        List<String> msgs = redisMQ.consume(redisMQ.getRoutes().get(0).getList());
        int len;
        if (null != msgs && 0 < (len = msgs.size())) {
            // 将每一条消息转为JSONObject
            JSONObject jObj;
            for (int i = 0; i < len; i++) {
                if (!StringUtils.isEmpty(msgs.get(i))) {
                    jObj = JSONObject.parseObject(msgs.get(i));
                    // 取出消息
                    System.out.println(jObj.toJSONString());
                }
            }
        }
    }
}
