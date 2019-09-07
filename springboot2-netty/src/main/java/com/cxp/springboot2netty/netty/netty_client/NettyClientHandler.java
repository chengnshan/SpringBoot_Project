package com.cxp.springboot2netty.netty.netty_client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @program: netty
 * @description:
 * @author: cheng
 * @create: 2019-09-02 20:59
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("收到服务端消息: " + msg);
    }

}
