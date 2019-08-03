package com.cxp.springboot2rabbitmq.rabbitDLX;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *  模拟用户订单需要在30分钟内付款，到期失败
 * 死信队伍配置
 * @author 程
 * @date 2019/7/6 上午9:02
 */
@Configuration
public class DLXConfig {

    /* 定义死信队列相关信息*/
    public final static String deadQueueName = "dead_queue_dlx";
    public final static String deadRoutingKey = "dead_routing_key_dlx";
    public final static String deadExchangeName = "dead_exchange_dlx";
    /**死信队列 交换机标识符*/
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**死信队列交换机绑定键标识符*/
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * 普通队列、交换机、路由键
     */
    public static final String REAL_EXCHANGE_NAME = "real_exchange_dlx";
    public static final String REAL_QUEUE = "real_queue_dlx";
    public static final String REAL_ROUTINGKEY = "real_routingkey_dlx";

    @Bean
    public Queue deadQueue() {
        return new Queue(deadQueueName,true,false,false);
    }
    /**
     * 创建死信交换机
     * @return
     */
    @Bean
    public TopicExchange deadExchange(){
        return new TopicExchange(deadExchangeName,true,false,null);
    }

    /**
     * 死信队列与死信交换机绑定
     * @return
     */
    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(deadQueue())
                .to(deadExchange())
                .with(deadRoutingKey);
    }

    //===================================

    /**
     * 创建并绑定实际监听的消费队列 - 面向消费者
     * @return
     */
    /**
     * 定义普通队列
     * @return
     */
    @Bean
    public Queue realQueue(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange",deadExchangeName);
        args.put("x-dead-letter-routing-key",deadRoutingKey);
        args.put("x-message-ttl",30000);
        return new Queue(REAL_QUEUE,true,false,false,args);
    }

    @Bean
    public TopicExchange realExchange(){
        return new TopicExchange(REAL_EXCHANGE_NAME,true,false,null);
    }

    @Bean
    public Binding userOrderRealBinding(){
        return BindingBuilder.bind(realQueue())
                .to(realExchange())
                .with(REAL_ROUTINGKEY);
    }

}
