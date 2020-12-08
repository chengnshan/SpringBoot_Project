package com.cxp.rocketmq.producer.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Log4j2
public class ProducerMessageController {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @RequestMapping(value = "/sendMessage")
    public SendResult sendMessage(HttpServletRequest request,String input){
        log.info("开始发送消息："+input);
        Message sendMsg = new Message("DemoTopic","DemoTag", input.getBytes());
        //默认3秒超时
        SendResult sendResult = null;
        try {
            sendResult = defaultMQProducer.send(sendMsg);
        } catch (MQClientException | RemotingException | MQBrokerException |InterruptedException e) {
            e.printStackTrace();
        }
        log.info("消息发送响应信息："+sendResult.toString());
        return sendResult;
    }
}
