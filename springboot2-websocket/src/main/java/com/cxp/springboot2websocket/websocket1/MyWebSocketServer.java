package com.cxp.springboot2websocket.websocket1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : cheng
 * @date : 2019-11-02 20:52
 */
@ServerEndpoint(value = "/websocket1/{custId}")
@Component
public class MyWebSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<MyWebSocketServer> webSocketSet = new CopyOnWriteArraySet<MyWebSocketServer>();

    private static Map<String,MyWebSocketServer> sessionPool = new HashMap<String,MyWebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private static Logger log = LoggerFactory.getLogger("fileInfoLog");

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "custId")String custId) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        sessionPool.put(custId, this);
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
//        try {
//            sendMessage("有新连接加入！当前在线人数为" + getOnlineCount());
//        } catch (IOException e) {
//            System.out.println("IO异常");
//        }
        Map<String,Object> map = new HashMap<>(16);
        map.put("users",sessionPool.keySet());
        //遍历发送消息
        Collection<MyWebSocketServer> values = sessionPool.values();
        values.forEach( us ->{
            try {
                us.sendMessage(JSON.toJSONString(map));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        webSocketSet.remove(this);
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());

        if (sessionPool.values().contains(this)){
            sessionPool.values().remove(this);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        this.session = session;
        log.info("来自客户端的消息:" + message);
        try {
            HashMap hashMap = JSON.parseObject(message, HashMap.class);

            Map<String,Object> map = new HashMap<>(16);

            JSONArray users = (JSONArray) hashMap.get("users");
            users.forEach(arr -> {
                MyWebSocketServer myWeb = sessionPool.get(arr);
                try {
                    map.put("message",hashMap.get("msg"));
                    myWeb.sendMessage(JSON.toJSONString(map));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            log.error("onMessage方法异常"+e.toString());
            e.printStackTrace();
        }
        //群发消息
//        sendInfo("群发消息"+message);
    }

    /**
     * 发生错误时调用
     @OnError
     **/
    public void onError(Session session, Throwable error) {
        log.error("onMessage方法异常"+error.toString());
        error.printStackTrace();
    }


    /**
     * 发送消息需注意方法加锁synchronized，避免阻塞报错
     * 注意session.getBasicRemote()与session.getAsyncRemote()的区别
     * @param message
     * @throws IOException
     */
    public synchronized void sendMessage(String message) throws IOException {
//         this.session.getBasicRemote().sendText(message);
        this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        for (MyWebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized AtomicInteger getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocketServer.onlineCount.getAndIncrement();
    }

    public static synchronized void subOnlineCount() {
        MyWebSocketServer.onlineCount.getAndDecrement();
    }

}
