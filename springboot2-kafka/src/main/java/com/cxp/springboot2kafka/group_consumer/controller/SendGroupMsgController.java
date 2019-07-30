package com.cxp.springboot2kafka.group_consumer.controller;

import com.cxp.springboot2kafka.constant.Topic;
import com.cxp.springboot2kafka.group_consumer.kafkaproducer.KafKaGroupCustomrProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多消费者组、组中多消费者对同一主题的消费情况
 *
 * @author 程
 * @date 2019/7/29 下午6:28
 */
@Slf4j
@RestController
public class SendGroupMsgController {

    @Autowired
    private KafKaGroupCustomrProducer producer;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /***
     * 发送消息体为基本类型的消息
     */
    @GetMapping("sendGroupMsg")
    public void sendGroupMsg() {
        for (int i = 0; i < 4; i++) {
            producer.sendMessage(Topic.GROUP,i%4,"key",
                    "hello group " + i);
        }
    }

}
