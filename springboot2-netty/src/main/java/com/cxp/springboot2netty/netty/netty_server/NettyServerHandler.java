package com.cxp.springboot2netty.netty.netty_server;

import com.alibaba.fastjson.JSON;
import com.cxp.springboot2netty.config.common.SpringApplicationHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 *
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-02 20:38
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        StringBuilder sb = null;
        Map<String, Object> result = null;
        try {
            // 报文解析处理
            sb = new StringBuilder();
            String message = (String) msg;
            result = JSON.parseObject(message);

            sb.append(result);
            sb.append("解析成功");
            sb.append("$_");
            ctx.writeAndFlush(sb);
        } catch (Exception e) {
            String errorCode = "-1\n";
            ctx.writeAndFlush(errorCode);
            log.error("报文解析失败: " + e.getMessage());
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * channelActive，ChannelHandlerContext的Channel已激活
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inetSocketAddress.getAddress().getHostAddress();
        int port = inetSocketAddress.getPort();
        log.info("收到客户端[ip:" + clientIp + ", 端口: "+port+" ]连接");
        SpringApplicationHolder.channelMap.put(clientIp,ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(),cause);
        // 当出现异常就关闭连接
        ctx.close();
    }

    /**
     * 一个用户事件被触发
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("userEventTriggered === ");
        IdleStateEvent event =(IdleStateEvent)evt;
        String eventType = null;
        switch (event.state()){
            case READER_IDLE:
                eventType = "读空闲";
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                break;
            case ALL_IDLE:
                eventType ="读写空闲";
                break;
        }
        System.out.println(ctx.channel().remoteAddress() + "超时事件：" +eventType);
        super.userEventTriggered(ctx, evt);
    }

    /**
     * channelUnregistered，ChannelHandlerContext的Channel从EventLoop中注销
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        SocketAddress remoteAddress = ctx.channel().remoteAddress();
        log.info("channelUnregistered === "+remoteAddress+"已经失去连接!");
        super.channelUnregistered(ctx);
    }

    /**
     * channelInactive，ChannelHanderContxt的Channel结束生命周期
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * channelReadComplete，消息读取完成后执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
