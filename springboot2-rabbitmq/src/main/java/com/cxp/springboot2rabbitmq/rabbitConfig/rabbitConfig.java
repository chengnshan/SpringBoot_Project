package com.cxp.springboot2rabbitmq.rabbitConfig;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 程
 * @date 2019/6/29 下午8:28
 */
@Configuration
public class rabbitConfig {

    @Bean
    public Queue queue(){
        return null;
    }
}
