package com.cxp.springboot2rabbitmq.rabbitProducer.producer;

import com.cxp.springboot2rabbitmq.rabbitProducer.pojo.UserInfo;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author 程
 * @date 2019/7/9 下午12:05
 */
@RestController
public class FanoutProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static Integer num = 0;

    @RequestMapping(value = "/fanoutSend")
    public void fanoutSend(){
        UserInfo userInfo = new UserInfo(num++,"fanout","fanout","男",
                "fanout",new Date());

        Object receive = rabbitTemplate.convertSendAndReceive("fanoutExchange", null, userInfo,
                new CorrelationData(UUID.randomUUID().toString()));
        System.out.println(receive);
    }
}
