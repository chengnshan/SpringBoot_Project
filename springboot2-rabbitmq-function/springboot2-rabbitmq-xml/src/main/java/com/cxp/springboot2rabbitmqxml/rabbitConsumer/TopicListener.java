package com.cxp.springboot2rabbitmqxml.rabbitConsumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.io.IOException;

/**
 * @author 程
 * @date 2019/7/2 上午8:29
 */
public class TopicListener implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(TopicListener.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.info("getMessage : "+ new String(message.getBody()));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
