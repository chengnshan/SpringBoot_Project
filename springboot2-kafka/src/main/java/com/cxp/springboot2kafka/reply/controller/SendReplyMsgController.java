package com.cxp.springboot2kafka.reply.controller;

import com.cxp.springboot2kafka.constant.Topic;
import com.cxp.springboot2kafka.reply.kafkaproducer.KafKaCustomrReplyProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author 程
 * @date 2019/7/29 下午6:28
 */
@Slf4j
@RestController
public class SendReplyMsgController {

    @Autowired
    private KafKaCustomrReplyProducer<String,String,String> producer;

    @GetMapping(value = "sendReply")
    public void sendReply() throws ExecutionException, InterruptedException {
        /**ProducerRecord封装消息属性,对应的构造函数:
         *  topic：这里填写的是Topic的名字
         *  partition：这里填写的是分区的id，其实也是就第几个分区，id从0开始。表示指定发送到该分区中
         *  timestamp：时间戳，一般默认当前时间戳
         *  key：消息的键
         *  value：消息的数据
         */
        ProducerRecord<String,String> producerRecord1 = new ProducerRecord(Topic.REPLY,0,null,
                "This is one reply message!");
        producerRecord1.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC,
                "spring.boot.kafka.reply.return".getBytes()));

        String value1 = producer.sendAndReceive(producerRecord1);

        producer.sendMessage(Topic.REPLY,1,null,"This is two reply message!");

        System.out.println("=========producerRecord1 : "+value1);
    }
}
