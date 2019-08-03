package com.cxp.springboot2kafka.simple.controller;

import com.cxp.springboot2kafka.constant.Topic;
import com.cxp.springboot2kafka.simple.kafkaproducer.KafKaCustomrProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author 程
 * @date 2019/7/29 下午6:28
 */
@Slf4j
@RestController
public class SendMsgController {

    @Autowired
    private KafKaCustomrProducer producer;

    /***
     * 发送消息体为基本类型的消息
     */
    @GetMapping("sendSimple")
    public void sendSimple() {
        producer.sendMessage(Topic.SIMPLE, "hello spring boot kafka");
    }

}
