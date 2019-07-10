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
 * @date 2019/7/9 下午12:06
 */
@RestController
public class HeadersProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/headerSend")
    public void headerSend(){
        UserInfo userInfo = new UserInfo(2,"header","123456","男",
                "header",new Date());

        rabbitTemplate.convertAndSend("headersExchange", "",
                userInfo,
                message -> {
                    message.getMessageProperties().setHeader("x-headers","header_value");
                    return message;
                },
                new CorrelationData(UUID.randomUUID().toString()));
    }
}
