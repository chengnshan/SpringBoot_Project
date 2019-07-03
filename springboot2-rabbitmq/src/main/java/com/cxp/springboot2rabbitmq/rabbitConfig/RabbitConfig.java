package com.cxp.springboot2rabbitmq.rabbitConfig;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 程
 * @date 2019/7/1 下午8:17
 */
@Configuration
public class RabbitConfig {

    /**
     * 定义消息转换实例  转化成 JSON 传输  传输实体就可以不用实现序列化
     * 注意:如果发送方和接收方都用Jackson2JsonMessageConverter的，那它接收到消息的时候会根据发送方的实体类进行序列化，
     *     如果接收方没有这个实体类就会报错。
     * @return
     */
    @Bean
    public MessageConverter integrationEventMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


}
