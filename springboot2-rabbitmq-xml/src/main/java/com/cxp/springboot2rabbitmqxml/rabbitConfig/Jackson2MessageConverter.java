package com.cxp.springboot2rabbitmqxml.rabbitConfig;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;

/**
 * @author 程
 * @date 2019/7/2 上午10:52
 */
public class Jackson2MessageConverter implements MessageConverter {

    @Override
    public Object fromMessage(org.springframework.messaging.Message<?> message, Class<?> targetClass) {

        return null;
    }

    @Override
    public org.springframework.messaging.Message<?> toMessage(Object payload, MessageHeaders headers) {
        return null;
    }
}
