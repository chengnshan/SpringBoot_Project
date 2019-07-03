package com.cxp.springboot2rabbitmq.rabbitProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author 程
 * @date 2019/7/1 下午9:18
 */
@Component
public class RabbitSender implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    private final Logger logger = LoggerFactory.getLogger(RabbitSender.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 实现消息发送到RabbitMQ交换器后接收ack回调,如果消息发送确认失败就进行重试
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack && correlationData != null){
            logger.info("消息发送成功,消息ID:{}", correlationData.getId());
            Message returnedMessage = correlationData.getReturnedMessage();
            if (returnedMessage != null){
                try {
                    String returnMsg = new String(returnedMessage.getBody(),"UTF-8");
                    logger.info(returnMsg);
                } catch (UnsupportedEncodingException e) {
                    logger.error("confirm : "+e.getMessage(),e);
                }
            }
        }else {
            logger.info("消息发送失败，消息ID:{}", correlationData.getId());
        }
    }

    /**
     * 实现消息发送到RabbitMQ交换器,但无相应队列与交换器绑定时的回调.
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
                                String routingKey) {
        logger.error("消息发送失败，replyCode:{}, replyText:{}，exchange:{}，routingKey:{}，消息体:{}",
                replyCode, replyText, exchange, routingKey, new String(message.getBody()));
    }
}
