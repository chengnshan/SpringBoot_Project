package com.cxp.springboot2netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-02 19:57
 */
@Slf4j
@Component
public class NettyServer {

    @Value("${netty.port}")
    private int port;

    public void init(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                //ChannelOption.SO_BACKLOG对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来
                // 初始化服务端可连接队列，服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
                // 服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
                .option(ChannelOption.SO_BACKLOG,1024)
                //接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功。
                .option(ChannelOption.SO_SNDBUF,1024*10)
                .option(ChannelOption.SO_RCVBUF,1024*10)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输，于TCP_NODELAY相对应的是TCP_CORK，该选项是需要等到发送的数据量
                // 最大的时候，一次性发送数据，适用于文件传输。
                .childOption(ChannelOption.TCP_NODELAY,true)
                //该参数用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接。当设置该选项以后，
                // 如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new NettyServerInitializer());

        // 绑定端口,开始接收进来的连接
        try {
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("netty服务启动: [port:" + port + "]");
            // 等待服务器socket关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
