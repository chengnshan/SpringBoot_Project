package com.cxp.springboot2rabbitmq.rabbitConfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 程
 * @date 2019/7/1 上午8:55
 */
@Configuration
public class RabbitBindingConfig {

    @Autowired
    @Qualifier(value = "directQueue")
    private Queue directQueue;

    @Autowired
    @Qualifier(value = "topicQueue1")
    private Queue topicQueue1;

    @Autowired
    @Qualifier(value = "topicQueue2")
    private Queue topicQueue2;

    @Autowired
    @Qualifier(value = "headersQueue")
    private Queue headersQueue;

    @Autowired
    @Qualifier(value = "directExchange")
    private DirectExchange directExchange;

    @Autowired
    @Qualifier(value = "topicExchange")
    private TopicExchange topicExchange;

    @Autowired
    @Qualifier(value = "headersExchange")
    private HeadersExchange headersExchange;

    @Value(value = "${custom.rabbitmq.directRoutingKey}")
    private String directRoutingKey;

    @Value(value = "${custom.rabbitmq.topicRoutingKey1}")
    private String topicRoutingKey1;

    @Value(value = "${custom.rabbitmq.topicRoutingKey2}")
    private String topicRoutingKey2;

    @Value(value = "${custom.rabbitmq.header}")
    private String header;

    /**
     * 将direct队列和交换机进行绑定
     * @return
     */
    @Bean
    public Binding directBinding(){
        return BindingBuilder.bind(directQueue).to(directExchange).with(directRoutingKey);
    }

    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with(topicRoutingKey1);
    }

    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with(topicRoutingKey2);
    }

    /**
     * headerExchange类型交换机(头部需要携带key为x-headers ,value为header_value )
     * @return
     */
    @Bean
    public Binding headerBinding(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-headers",header);
        return BindingBuilder.bind(headersQueue).to(headersExchange).whereAll(map).match();
    }
}
