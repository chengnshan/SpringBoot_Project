package com.cxp.stompwebsocket.config;

import com.cxp.stompwebsocket.websocket.CustomHandshakeInterceptor;
import com.cxp.stompwebsocket.websocket.MyChannelInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.Map;

/**
 * 参考: https://zq99299.gitbooks.io/essay-note/content/chapter/websocket/spring/stomp.html
 * @author : cheng
 * @date : 2020-03-31 18:51
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 增加一个入口节点，并开其sockjs的支持，和相关的配置
        //  /stomp是WebSocket（或SockJS）客户端为WebSocket握手需要连接到的端点的HTTP URL
        registry.addEndpoint("/stomp")
                .setAllowedOrigins("http://localhost")
                .addInterceptors(new CustomHandshakeInterceptor());

        registry.addEndpoint("/socketJs/stomp").setAllowedOrigins("*").withSockJS()

                //流字节数限制--将streamBytesLimit属性设置为512KB（默认为128KB => 128 * 1024）
                .setStreamBytesLimit(512*1024)

                //http邮件缓存大小--将httpMessageCacheSize属性设置为1000（默认为100）
                .setHttpMessageCacheSize(1000)

                //将disconnectDelay属性设置为30秒（默认为5秒 => 5 * 1000）
                .setDisconnectDelay(30 * 1000)

                //setClientLibraryUrl解决报错
                //  Uncaught Error: Incompatibile SockJS! Main site uses: "1.1.4", the iframe: "1.0.0".
                //原因:服务器端使用的版本是1.0.0,而js中使用的是1.1.4
                // (官网解释:https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#websocket-fallback-xhr-vs-iframe)
                .setClientLibraryUrl("//cdn.jsdelivr.net/sockjs/1/sockjs.min.js");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 以/app开头的destination header的STOMP消息被路由到@Controller类中的@MessageMapping方法
        registry.setApplicationDestinationPrefixes("/app")
                //
                .setUserDestinationPrefix("/user")
                .setPathMatcher(new AntPathMatcher("/"))
                .setPreservePublishOrder(true);

        // 使用内置的消息代理进行订阅和广播，并将destination header 以/topic或/queue开头的消息路由到代理
        registry.enableSimpleBroker("/topic", "/queue");

        // 下面这配置为默认配置，如有变动修改配置启用就可以了
        registry.enableStompBrokerRelay("/rabbit/queue")
                //配置当Spring ApplicationContext刷新时是否应该自动启动
                .setAutoStartup(false)
                //Mq服务器地址
                .setRelayHost("127.0.0.1")
                //Mq服务器服务端口
                .setRelayPort(61613)
                .setVirtualHost("/")
                //Mq服务登录
                .setClientLogin("guest").setClientPasscode("guest");
    }

    /**
     * 配置客户端入站通道拦截器
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new MyChannelInterceptor(),
                //实现用户认证
                new ChannelInterceptor() {
                    @Override
                    public Message<?> preSend(Message<?> message, MessageChannel channel) {
                        StompHeaderAccessor accessor =
                                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                        //1. 判断是否首次连接请求
                        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                            //2. 验证是否登录
                            String username = accessor.getNativeHeader("username").get(0);
                            String password = accessor.getNativeHeader("password").get(0);

                        }
                        return message;
                    }
                });
        // 线程信息
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8).keepAliveSeconds(60);
    }

    /**
     * 将用于向WebSocket客户端出站消息的配置。默认情况下，通道由大小为1的线程池支持。建议自定义线程池
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new MyChannelInterceptor());
    }
}
