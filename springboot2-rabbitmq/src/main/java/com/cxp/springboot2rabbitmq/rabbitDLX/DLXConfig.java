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

    /**
     * 死信队列配置
     * @return
     */
    @Bean
    public Queue userOrderDeadQueue(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange","user.order.real.exchange");
        args.put("x-dead-letter-routing-key","user.order.real.routingkey");
        args.put("x-message-ttl","30000");
        return new Queue("user.order.dead.queue",true,false,false,args);
    }

    /**
     * 面向生产端 - 死信队列
     * @return
     */
    @Bean
    public TopicExchange userOrderDeadExchange(){
        return new TopicExchange("user.order.dead.exchange",true,false,null);
    }

    @Bean
    public Binding userOrderDeadBinding(){
        return BindingBuilder.bind(userOrderDeadQueue())
                .to(userOrderDeadExchange())
                .with("user.order.dead.routingkey");
    }

    //===================================

    /**
     * 创建并绑定实际监听的消费队列 - 面向消费者
     * @return
     */
    @Bean
    public Queue userOrderRealQueue(){
        return new Queue("user.order.real.queue",true,false,false,null);
    }

    @Bean
    public TopicExchange userOrderRealExchange(){
        return new TopicExchange("user.order.real.exchange",true,false,null);
    }

    @Bean
    public Binding userOrderRealBinding(){
        return BindingBuilder.bind(userOrderRealQueue())
                .to(userOrderRealExchange())
                .with("user.order.real.routingkey");
    }

}
