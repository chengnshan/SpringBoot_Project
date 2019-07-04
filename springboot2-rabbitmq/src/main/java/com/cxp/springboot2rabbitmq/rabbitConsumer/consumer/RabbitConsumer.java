package com.cxp.springboot2rabbitmq.rabbitConsumer.consumer;

import com.cxp.springboot2rabbitmq.rabbitConsumer.pojo.UserInfo;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 程
 * @date 2019/7/1 下午6:03
 */
@Component
public class RabbitConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConsumer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    @RabbitListener(queues = "topicQueue2")
    public String topicMessage1(UserInfo userInfo, Channel channel, Message message) throws Exception{
        LOGGER.info("topicMessage1 : "+userInfo.toString());
        try {
            channel.basicQos(1);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return " topicMessage1 success !";
        } catch (IOException e) {
            LOGGER.error("MQ消息处理异常，消息ID：{}，消息体:{}",
                    message.getMessageProperties().getCorrelationId(),
                    userInfo.toString(),e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
        return null;
    }


    @RabbitHandler
    @RabbitListener(queues = {"headersQueue"})
    public String headerMessage(UserInfo userInfo, Channel channel, Message message)throws Exception {
        LOGGER.info("headerMessage : " + userInfo.toString());
        try {
            channel.basicQos(1);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return " headerMessage success !";
        } catch (IOException e) {
            LOGGER.error("MQ消息处理异常，消息ID：{}，消息体:{}",
                    message.getMessageProperties().getCorrelationId(),
                    userInfo.toString(), e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
        return null;
    }

//    @RabbitHandler
 //   @RabbitListener(queues = {"fanoutQueue1"})
    public String fanoutMessage1(UserInfo userInfo, Channel channel, Message message) throws Exception{
        LOGGER.info("fanoutMessage1 : " + userInfo.toString());
        try {
            if (userInfo != null && userInfo.getId() % 2 == 1){
                int i = 10/0;
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return " fanoutMessage1 success !";
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
        return null;
    }

    @RabbitHandler
    @RabbitListener(queues = {"fanoutQueue2"})
    public String fanoutMessage2(UserInfo userInfo, Channel channel, Message message)throws Exception{
        LOGGER.info("fanoutMessage2 : " + userInfo.toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return " fanoutMessage2 success !";
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
        return null;
    }
}
