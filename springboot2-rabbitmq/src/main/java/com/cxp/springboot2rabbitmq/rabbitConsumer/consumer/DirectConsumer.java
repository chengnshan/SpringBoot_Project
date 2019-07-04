package com.cxp.springboot2rabbitmq.rabbitConsumer.consumer;

import com.cxp.springboot2rabbitmq.rabbitConsumer.pojo.UserInfo;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 根据不同的类型选择不同的监听方法处理
 * @author 程
 * @date 2019/7/4 下午8:35
 */
@Component
@RabbitListener(queues ={"directQueue"} )
public class DirectConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectConsumer.class);

    /**
     *
     * @param userInfo
     * @param channel
     * @param message
     * @return
     * @throws Exception
     */
    @RabbitHandler
    public String directMessage(UserInfo userInfo, Channel channel, Message message) throws Exception{
        LOGGER.info(userInfo.toString());
        try {
            /**
             * prefetchCount限制每个消费者在收到下一个确认回执前一次可以最大接受多少条消息,通过basic.qos方法
             * 设置prefetch_count=1,这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message
             */
            channel.basicQos(1);
            /**
             * 确认消息已经消费成功
             * 第一个参数 deliveryTag：就是接受的消息的deliveryTag,可以通过msg.getMessageProperties().getDeliveryTag()获得
             * 第二个参数 multiple：如果为true，确认之前接受到的消息；如果为false，只确认当前消息。
             *
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return " directMessage success !";
        } catch (IOException e) {
            LOGGER.error("MQ消息处理异常，消息ID：{}，消息体:{}",
                    message.getMessageProperties().getCorrelationId(),
                    userInfo.toString(),e);
            //拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
        return null;
    }

    /**
     *  接收list集合的对象消息
     * @param userInfos
     * @param channel
     * @param message
     * @return
     * @throws Exception
     */
    @RabbitHandler
    public String directMessage(List<UserInfo> userInfos, Channel channel, Message message) throws Exception{
        userInfos.forEach((userInfo)-> System.out.println(userInfo));
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return " directMessage success !";
        } catch (IOException e) {
            LOGGER.error("MQ消息处理异常，消息ID：{}，消息体:{}",
                    message.getMessageProperties().getCorrelationId(),
                    userInfos,e);
            //拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
        return null;
    }

    /**
     *  接收Map集合的对象消息
     * @param userMap
     * @param channel
     * @param message
     * @return
     * @throws Exception
     */
    @RabbitHandler
    public String directMessage(Map<String,Object> userMap, Channel channel, Message message) throws Exception{
        if (!CollectionUtils.isEmpty(userMap)){
            userMap.keySet().forEach(key -> System.out.println(key + " -> " +userMap.get(key)));
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return " directMessage success !";
        } catch (IOException e) {
            LOGGER.error("MQ消息处理异常，消息ID：{}，消息体:{}",
                    message.getMessageProperties().getCorrelationId(),
                    userMap,e);
            //拒绝当前消息，并把消息返回原队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
        return null;
    }
}
