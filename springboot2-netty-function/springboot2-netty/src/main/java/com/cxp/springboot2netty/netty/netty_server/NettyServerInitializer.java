package com.cxp.springboot2netty.netty.netty_server;

import com.cxp.springboot2netty.jboss.MarshallingCodeCFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-02 20:25
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //设置特殊分隔符
    //    ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes());
    //    pipeline.addLast(new DelimiterBasedFrameDecoder(8192, byteBuf));
        //netty自带的解码实现
        pipeline.addLast("decoder", new ObjectDecoder(1024*1024,
                ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        //netty自带的编码实现
        pipeline.addLast("encoder", new ObjectEncoder());
        //服务端添加IdleStateHandler心跳检测处理器，并添加自定义处理Handler类实现userEventTriggered()方法作为超时事件的逻辑处理；
        /**
         * IdleStateHandler构造器
         *  readerIdleTime读空闲超时时间设定，如果channelRead()方法超过readerIdleTime时间未被调用则会触发超时事件调用userEventTrigger()方法；
         *  writerIdleTime写空闲超时时间设定，如果write()方法超过writerIdleTime时间未被调用则会触发超时事件调用userEventTrigger()方法
         *  allIdleTime所有类型的空闲超时时间设定，包括读空闲和写空闲
         *  unit时间单位，包括时分秒等
         */
        pipeline.addLast("loginAuth",new LoginAuthHandler());
        //pipeline.addLast("idle",new IdleStateHandler(40,0,0,TimeUnit.SECONDS)); 该代码实现了心跳检测，每隔40s检测一次是否要读事件，
        // 如果超过40s你没有读事件的发生，则执行相应的操作（在handler中实现）
    //    pipeline.addLast("idle",new IdleStateHandler(40,0,0, TimeUnit.SECONDS));
        pipeline.addLast("serverHandler",new NettyServerHandler());
    }
}
