package com.cxp.stompwebsocket.websocket.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * @author : cheng
 * @date : 2020-04-04 00:18
 */
@Configuration
public class StompEvents implements ApplicationListener<SessionSubscribeEvent> {

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        System.out.println(event);
    }
}
