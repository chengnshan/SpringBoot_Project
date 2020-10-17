package com.cxp.websocketsession.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * @author : cheng
 * @date : 2020-04-10 20:56
 */
public class WebSocketConectHandler<S> implements ApplicationListener<SessionConnectEvent> {

    private SimpMessageSendingOperations messagingTemplate;

    public WebSocketConectHandler(SimpMessageSendingOperations messagingTemplate){
            super();
            this.messagingTemplate=messagingTemplate;
    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
    }
}
