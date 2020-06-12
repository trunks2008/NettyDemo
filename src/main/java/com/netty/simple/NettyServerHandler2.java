package com.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


public class NettyServerHandler2 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //比如这里我们有一个非常耗时的业务 -> 异步执行 ->提交改channel对应的NioEventLoop的 taskQueue中
        //解决方案1：用户程序自定义的普通任务
        //下面的方式相当于把任务提交到了taskQueue中
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("发生异常："+e.getMessage());
                }
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,after read message",CharsetUtil.UTF_8));
            }
        });

//        ctx.channel().eventLoop().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TimeUnit.SECONDS.sleep(20);
//                } catch (InterruptedException e) {
//                    System.out.println("发生异常："+e.getMessage());
//                }
//                ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,after read message2",CharsetUtil.UTF_8));
//            }
//        });


        System.out.println("go on");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
