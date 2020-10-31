package com.cxp.springboot2netty.netty.nettyclient.client1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @program: netty
 * @description:
 * @author: cheng
 * @create: 2019-09-02 20:54
 */
public class NettyClient {

    private  int port;
    private  String address;

    public NettyClient(int port,String address) {
        this.port = port;
        this.address = address;
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new NettyClientInitializer());

        try {
            ChannelFuture future = bootstrap.connect(address,port).sync();
            Channel channel = future.channel();
            // 发送json字符串
            String msg = "{\"name\":\"admin\",\"age\":27}$_";
            channel.writeAndFlush(msg);
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 10999;
        NettyClient nettyClient = new NettyClient(port, host);
        nettyClient.start();
    }
}
