package com.cxp.springboot2rabbitmqxml.rabbitConsumer;

import com.cxp.springboot2rabbitmqxml.pojo.UserInfo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.UUID;

/**
 * @author 程
 * @date 2019/7/3 下午2:50
 */
public class DirectListener  {

    public String onMessage(UserInfo userInfo,Message message,Channel channel){
        try{
            System.out.println("DirectListener onMessage 消费."+ userInfo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("DirectListener onMessage 消费成功!"+ userInfo);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
