package com.cxp.springboot2netty.controller;

import com.cxp.springboot2netty.config.common.NettyChannelMap;
import com.cxp.springboot2netty.pojo.MsgType;
import com.cxp.springboot2netty.pojo.SubscribeMsg;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.SocketAddress;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-04 22:52
 */
@Controller
@Slf4j
public class ChannelController {

    @RequestMapping(value = "/channel/sendMsg")
    @ResponseBody
    public String sendMsg(String message){
        SocketChannel channel = (SocketChannel) NettyChannelMap.get("127.0.0.1");
        StringBuilder stringBuilder = new StringBuilder();
        SocketAddress localAddress = channel.localAddress();
        SocketAddress remoteAddress = channel.remoteAddress();
        stringBuilder.append("本地服务器地址: ");
        stringBuilder.append(localAddress);
        stringBuilder.append(",向远程客户端地址:");
        stringBuilder.append(remoteAddress);
        stringBuilder.append("发送消息");
        log.info(stringBuilder.toString());

        SubscribeMsg resp = new SubscribeMsg(null,"这是服务端ChannelController发送来的消息!"+System.currentTimeMillis());
        resp.setMsgType(MsgType.REPLY);

        ChannelFuture channelFuture = channel.writeAndFlush(resp);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.info("success{}", "发送成功!");
                } else {
                    log.error("sendMsg Throwable : " + future.cause().getMessage(), future.cause());
                }
            }
        });
        return null;
    }
}
