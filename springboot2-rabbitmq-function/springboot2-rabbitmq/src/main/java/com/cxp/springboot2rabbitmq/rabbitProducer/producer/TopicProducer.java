package com.cxp.springboot2rabbitmq.rabbitProducer.producer;

import com.cxp.springboot2rabbitmq.rabbitProducer.pojo.UserInfo;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author 程
 * @date 2019/7/9 下午12:07
 */
@RestController
public class TopicProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${custom.rabbitmq.exchange.topic}")
    private String topicExchange;

    @Value(value = "${custom.rabbitmq.topicRoutingKey1}")
    private String topicRoutingKey1;

    @Value(value = "${custom.rabbitmq.topicRoutingKey2}")
    private String topicRoutingKey2;

    /**
     * 此方法发送的消息会匹配到队列topicQueue1(路由键key.topic)和topicQueue2(路由键key.#)这两个队列
     */
    @RequestMapping(value = "/topicSend1")
    public void topicSend1(){
        UserInfo userInfo = new UserInfo(2,"李世民","123456","男",
                "唐太宗",new Date());
        Object receive = rabbitTemplate.convertSendAndReceive(topicExchange, topicRoutingKey1, userInfo,
                new CorrelationData(UUID.randomUUID().toString()));
        System.out.println("topicSend1 : " + receive);
    }

    /**
     * 此方法发送的消息会匹配到队列topicQueue2(路由键key.#)这两个队列
     */
    @RequestMapping(value = "/topicSend2")
    public void topicSend2(){
        UserInfo userInfo = null;
        for (int i =0;i<4;i++){
            userInfo = new UserInfo(i,"王五","123456","男",
                    "张真人",new Date());
            rabbitTemplate.convertAndSend(topicExchange, topicRoutingKey2, userInfo,
                    new CorrelationData(UUID.randomUUID().toString()));

        }
    }
}
