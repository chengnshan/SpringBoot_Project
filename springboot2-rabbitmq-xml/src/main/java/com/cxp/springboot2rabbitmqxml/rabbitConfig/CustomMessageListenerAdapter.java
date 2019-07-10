package com.cxp.springboot2rabbitmqxml.rabbitConfig;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpIllegalStateException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.adapter.InvocationResult;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author 程
 * @date 2019/7/10 下午1:05
 */
public class CustomMessageListenerAdapter extends MessageListenerAdapter {

    public  CustomMessageListenerAdapter(){}

    public CustomMessageListenerAdapter(Object delegate) {
        super(delegate);
    }

    public CustomMessageListenerAdapter(Object delegate, MessageConverter messageConverter) {
        super(delegate,messageConverter);
    }

    public CustomMessageListenerAdapter(Object delegate, String defaultListenerMethod) {
        super(delegate,defaultListenerMethod);
    }

    protected Object[] buildListenerArguments(Object extractedMessage,Message message,Channel channel) {
        return new Object[] {extractedMessage,message,channel};
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        Object delegateListener = getDelegate();
        if (delegateListener != this) {
            if (delegateListener instanceof ChannelAwareMessageListener) {
                ((ChannelAwareMessageListener) delegateListener).onMessage(message, channel);
                return;
            }
            else if (delegateListener instanceof MessageListener) {
                ((MessageListener) delegateListener).onMessage(message);
                return;
            }
        }

        // Regular case: find a handler method reflectively.
        Object convertedMessage = extractMessage(message);
        String methodName = getListenerMethodName(message, convertedMessage);
        if (methodName == null) {
            throw new AmqpIllegalStateException("No default listener method specified: "
                    + "Either specify a non-null value for the 'defaultListenerMethod' property or "
                    + "override the 'getListenerMethodName' method.");
        }

        // Invoke the handler method with appropriate arguments.
        Object[] listenerArguments = buildListenerArguments(convertedMessage,message,channel);
        Object result = invokeListenerMethod(methodName, listenerArguments, message);
        if (result != null) {
            handleResult(new InvocationResult(result, null, null), message, channel);
        }
        else {
            logger.trace("No result object given - no result to handle");
        }
    }
}
