package com.cxp.springboot2rabbitmq.rabbitDLX;

import com.cxp.springboot2rabbitmq.rabbitConsumer.pojo.UserInfo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 程
 * @date 2019/7/6 上午9:21
 */
@Component
public class DLXConsumer {

    /**
     * 监听绑定了死信队列的 普通队列
     * @param userInfo
     * @param message
     * @param headers
     * @param channel
     * @throws Exception
     */
    @RabbitListener(queues = {"real_queue_dlx"})
    public void process(UserInfo userInfo,Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {

        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("获取生产者消息msg:"+ msg );

        try{
            if(userInfo != null && userInfo.getId() % 2 ==0){
                int i = 10 /0 ;
            }
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            // 手动签收
            channel.basicAck(deliveryTag,false);
        }catch (Exception e){
            e.printStackTrace();
            //拒绝消费消息（丢失消息） 给死信队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }

    /**
     * 监听死信队列中的消息
     * @param message
     * @param headers
     * @param channel
     */
    @RabbitListener(queues = {DLXConfig.deadQueueName})
    public void processDlx(Message message, @Headers Map<String, Object> headers,
            @Header("x-death") Map<String,Object> xDeath, Channel channel) throws Exception{
        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("获取死信队列消息msg: "+ msg );

        System.out.println("获取死信息队列中 x-death 头部信息: "+xDeath);

        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
