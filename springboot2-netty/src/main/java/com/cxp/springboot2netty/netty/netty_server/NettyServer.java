package com.cxp.springboot2netty.netty.netty_server;

import com.cxp.springboot2netty.config.netty.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private NettyProperties nettyProperties;

    /**
     * option主要是针对boss线程组，child主要是针对worker线程组
     */
    public void init(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                //Server是NioServerSocketChannel 客户端是NioSocketChannel绑定了jdk NIO创建的ServerSocketChannel对象,
                //用它来建立新accept的连接
                .channel(NioServerSocketChannel.class)
        // 第2次握手服务端向客户端发送请求确认，同时把此连接放入队列A中，
        // 然后客户端接受到服务端返回的请求后，再次向服务端发送请求，表示准备完毕，此时服务端收到请求，把这个连接从队列A移动到队列B中，
        // 此时A+B的总数，不能超过SO_BACKLOG的数值，满了之后无法建立新的TCP连接,2次握手后和3次握手后的总数
        // 当服务端从队列B中按照FIFO的原则获取到连接并且建立连接[ServerSocket.accept()]后，B中对应的连接会被移除，这样A+B的数值就会变小
        //此参数对于程序的连接数没影响，会影响正在准备建立连接的握手。
                .option(ChannelOption.SO_BACKLOG,nettyProperties.getSoBacklog())
                //接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功。
                .childOption(ChannelOption.SO_SNDBUF,1024 * 32)
                .childOption(ChannelOption.SO_RCVBUF,1024 * 32)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输，于TCP_NODELAY相对应的是TCP_CORK，该选项是需要等到发送的数据量
                // 最大的时候，一次性发送数据，适用于文件传输。
                .childOption(ChannelOption.TCP_NODELAY,true)
                //该参数用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，这个选项用于可能长时间没有数据交流的连接。当设置该选项以后，
                // 如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //是否允许重复绑定端口，重复启动，会把端口从上一个使用者上抢过来
                .childOption(ChannelOption.SO_REUSEADDR,true)
                //连接超时30000毫秒
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,30000)
                //输入流的read方法被阻塞时，接受数据的等待超时时间5000毫秒，抛出SocketException
         //       .option(ChannelOption.SO_TIMEOUT,5000)
                .childHandler(new NettyServerInitializer());

        // 绑定端口,开始接收进来的连接
        try {
            ChannelFuture channelFuture = bootstrap.bind(nettyProperties.getPort()).sync();
            log.info("netty服务启动: [port:" + nettyProperties.getPort() + "]");
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
