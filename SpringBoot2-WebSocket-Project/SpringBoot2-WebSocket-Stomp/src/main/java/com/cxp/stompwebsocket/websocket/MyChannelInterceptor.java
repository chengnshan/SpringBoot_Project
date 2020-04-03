package com.cxp.stompwebsocket.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.security.Principal;
import java.util.List;

/**
 * @author : cheng
 * @date : 2020-04-03 15:10
 */
@Slf4j
public class MyChannelInterceptor implements ChannelInterceptor {

    /**
     * 在调用receive和实际检索消息之前立即调用。如果返回值为“false”，则不会检索任何消息。这只适用于PollableChannels。
     * @param channel
     * @return
     */
    @Override
    public boolean preReceive(MessageChannel channel) {
        log.info("{}","MyChannelInterceptor preReceive...");
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        log.info("{}","MyChannelInterceptor postReceive...");
        return message;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        log.info("{}","MyChannelInterceptor afterReceiveCompletion...");
    }

    /**
     * 在消息实际发送到通道之前调用,这允许在必要时修改消息,如果这个方法返回，那么实际的不会发生send调用。
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompHeaderAccessor accessor1 = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor1 != null && StompCommand.CONNECT.equals(accessor1.getCommand())){
            String username = accessor1.getFirstNativeHeader("username");
            String password = accessor1.getFirstNativeHeader("password");
            log.info("username : {},password : {}",username,password);

        }
        return message;
    }

    /**
     * 在发送调用之后立即调用。布尔值参数表示调用的返回值。
     * @param message
     * @param channel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        log.info("{}","MyChannelInterceptor postSend...");
    }
}
