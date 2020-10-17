package com.cxp.websocketsession.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * @author : cheng
 * @date : 2020-04-10 21:03
 */
public class WebSocketDisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {

    private SimpMessageSendingOperations messagingTemplate;


    public WebSocketDisconnectHandler(SimpMessageSendingOperations messagingTemplate){
        super();
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        if (sessionId == null){
            return;
        }
    }

}
