package com.cxp.springboot2websocket.websocket2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author 程
 * @date 2019/7/15 下午6:23
 */
public class ChatMessageHandler extends TextWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(ChatMessageHandler.class);

    public static CopyOnWriteArraySet<WebSocketSession> webSocketSessions = new CopyOnWriteArraySet<WebSocketSession>();
    /**
     * 连接成功时候，会触发UI上onopen方法
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connect to the websocket success......");
        webSocketSessions.add(session);
        super.afterConnectionEstablished(session);
    }

    /**
     * 在UI在用js调用websocket.send()时候，会调用该方法
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("客户端消息: {}", message.getPayload());
        String websocketUsername = (String) session.getAttributes().get("websocket_username");
        sendMessageToUser(websocketUsername,message);
    }

    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : webSocketSessions) {
            if (user.getAttributes().get("websocket_username").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.debug("websocket connection closed......");
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.debug("websocket connection closed......");
        super.afterConnectionClosed(session, status);
    }

    @Override
    public boolean supportsPartialMessages() {
        return super.supportsPartialMessages();
    }
}
