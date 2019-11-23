package com.cxp.springboot2netty.netty.nettyclient.client1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty
 * @description:
 * @author: cheng
 * @create: 2019-09-02 20:59
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("收到服务端消息: " + msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("断线了。。。。。。");
        //使用过程中断线重连
        EventLoop eventLoop = ctx.channel().eventLoop();
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("READER_IDLE");

            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                /**发送心跳,保持长连接*/
                String  s = "ping$_";
                ctx.channel().writeAndFlush(s);
                log.debug("心跳发送成功!");
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("ALL_IDLE");
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
