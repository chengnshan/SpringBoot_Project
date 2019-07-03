package com.cxp.springboot2rabbitmq.rabbitConfig;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 程
 * @date 2019/7/1 上午8:50
 */
@Configuration
public class RabbitExchangeConfig {

    /**
     *  FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
        HeadersExchange: 通过添加属性key-value匹配
        DirectExchange: 按照routingkey分发到指定队列
        TopicExchange: 多关键字匹配(*代表匹配一个字符，#代表匹配多个字符)
     */

    @Value(value = "${custom.rabbitmq.exchange.direct}")
    private String directExchange;

    @Value(value = "${custom.rabbitmq.exchange.topic}")
    private String topicExchange;

    @Value(value = "${custom.rabbitmq.exchange.header}")
    private String headersExchange;

    /**
     * direct类型交换机
     * @return
     */
    @Bean(value = "directExchange")
    public DirectExchange directExchange(){
        return new DirectExchange(directExchange,true,false,null);
    }

    /**
     * topic类型交换机
     * @return
     */
    @Bean(name = "topicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange(topicExchange,true,false,null);
    }

    /**
     * headers类型交换机
     * @return
     */
    @Bean(name = "headersExchange")
    public HeadersExchange HeadersExchange(){
        return new HeadersExchange(headersExchange,true,false,null);
    }

}
