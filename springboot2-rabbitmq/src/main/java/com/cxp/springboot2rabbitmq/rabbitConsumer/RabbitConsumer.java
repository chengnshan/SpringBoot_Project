package com.cxp.springboot2rabbitmq.rabbitConsumer;

import com.cxp.springboot2rabbitmq.pojo.UserInfo;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 程
 * @date 2019/7/1 下午6:03
 */
@Component
public class RabbitConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConsumer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${custom.rabbitmq.queue.direct}")
    private String directQueue;

    @Value(value = "${custom.rabbitmq.exchange.direct}")
    private String directExchange;

    @Value(value = "${custom.rabbitmq.directRoutingKey}")
    private String directRoutingKey;

    @RabbitHandler
    @RabbitListener(queues = "directQueue")
    public void directMessage(UserInfo userInfo, Channel channel, Message message) throws Exception{
        LOGGER.info(userInfo.toString());
        try {
            /**
             * prefetchCount限制每个消费者在收到下一个确认回执前一次可以最大接受多少条消息,通过basic.qos方法
             * 设置prefetch_count=1,这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message
             */
            channel.basicQos(1);
            /**
             * 确认消息已经消费成功
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            LOGGER.error("MQ消息处理异常，消息ID：{}，消息体:{}",
                    message.getMessageProperties().getCorrelationId(),
                    userInfo.toString(),e);
            //拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
    }

    @RabbitHandler
    @RabbitListener(queues = "topicQueue2")
    public void topicMessage1(UserInfo userInfo, Channel channel, Message message) throws Exception{
        LOGGER.info("topicMessage1 : "+userInfo.toString());
        try {
            channel.basicQos(1);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            LOGGER.error("MQ消息处理异常，消息ID：{}，消息体:{}",
                    message.getMessageProperties().getCorrelationId(),
                    userInfo.toString(),e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
    }


    @RabbitHandler
    @RabbitListener(queues = {"headersQueue"})
    public void headerMessage(UserInfo userInfo, Channel channel, Message message)throws Exception {
        LOGGER.info("headerMessage : " + userInfo.toString());
        try {
            channel.basicQos(1);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            LOGGER.error("MQ消息处理异常，消息ID：{}，消息体:{}",
                    message.getMessageProperties().getCorrelationId(),
                    userInfo.toString(), e);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
