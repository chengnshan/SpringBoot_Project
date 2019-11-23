package com.cxp.springboot2netty.netty.netty_server;

import com.cxp.springboot2netty.pojo.BaseMsg;
import com.cxp.springboot2netty.pojo.LoginMsg;
import com.cxp.springboot2netty.pojo.MsgType;
import com.cxp.springboot2netty.pojo.SubscribeMsg;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-07 17:30
 */
public class LoginAuthHandler extends ChannelInboundHandlerAdapter {

    private boolean auth(ChannelHandlerContext ctx,BaseMsg msg){
        LoginMsg loginMsg = (LoginMsg) msg;
        if ("localhost".equals(loginMsg.getUsername())){
            if ("654321".equals(loginMsg.getPassword())){
                SubscribeMsg resp = new SubscribeMsg(null, "Login Success!");
                resp.setMsgType(MsgType.REPLY);
                ctx.writeAndFlush(resp);
                return true;
            }else {
                ctx.writeAndFlush("auth failure ! $_").addListener(ChannelFutureListener.CLOSE);
                return false;
            }
        }else {
            ctx.writeAndFlush("auth failure ! $_").addListener(ChannelFutureListener.CLOSE);
            return false;
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof BaseMsg){
            BaseMsg message = (BaseMsg) msg;
            if (message.getMsgType() == MsgType.LOGIN){
                auth(ctx, message);
            }
        }
        ctx.fireChannelRead(msg);
    }
}
