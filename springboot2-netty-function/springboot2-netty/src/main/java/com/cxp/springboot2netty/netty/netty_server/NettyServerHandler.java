package com.cxp.springboot2netty.netty.netty_server;

import com.alibaba.fastjson.JSON;
import com.cxp.springboot2netty.ConstantCommon.ConstantClass;
import com.cxp.springboot2netty.config.common.NettyChannelMap;
import com.cxp.springboot2netty.config.common.SpringApplicationHolder;
import com.cxp.springboot2netty.pojo.BaseMsg;
import com.cxp.springboot2netty.pojo.MsgType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

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

    /**
     * 心跳丢失次数
     */
    private int counter = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //重置心跳丢失次数
        counter = 0;
        try {
            if (msg instanceof BaseMsg){
                BaseMsg baseMsg = (BaseMsg) msg;
                //心跳信息
                if (MsgType.PING.equals(baseMsg.getMsgType())){
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
                    String clientIp = inetSocketAddress.getAddress().getHostAddress();
                    if (!NettyChannelMap.contains(clientIp)){
                        NettyChannelMap.add(clientIp,(SocketChannel) ctx.channel());
                    }
                    log.info("收到客户端心跳包!");
                }else if (MsgType.REPLY.equals(baseMsg.getMsgType())){
                    log.info(" 收到客户端消息 : " + baseMsg);
                }
            }
        } catch (Exception e) {
            String errorCode = "-1\n";
            ctx.writeAndFlush(errorCode);
            log.error("报文解析失败: " + e.getMessage(),e);
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
        String eventType = null;
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event =(IdleStateEvent)evt;
            switch (event.state()){
                case READER_IDLE:
                    eventType = "读空闲";
                    // 空闲40s之后触发 (心跳包丢失)
                    if (counter >= 3) {
                        // 连续丢失3个心跳包 (断开连接)
                        ctx.channel().close().sync();
                        log.error("已与"+ctx.channel().remoteAddress()+"断开连接");
                        System.out.println("已与"+ctx.channel().remoteAddress()+"断开连接");
                    } else {
                        counter++;
                        log.debug(ctx.channel().remoteAddress() + "丢失了第 " + counter + " 个心跳包");
                        System.out.println("丢失了第 " + counter + " 个心跳包");
                    }
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType ="读写空闲";
                    break;
                default:
                    break;
            }
        }

        System.out.println(ctx.channel().remoteAddress() + "超时事件：" +eventType);
    //    super.userEventTriggered(ctx, evt);
    }

    /**
     * channelUnregistered，ChannelHandlerContext的Channel从EventLoop中注销
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * channelInactive，ChannelHanderContxt的Channel结束生命周期
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress remoteAddress = ctx.channel().remoteAddress();
        log.info("channelUnregistered === "+remoteAddress+"已经失去连接!");
        NettyChannelMap.remove((SocketChannel) ctx.channel());
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
