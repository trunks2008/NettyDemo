package com.netty.simple;

import com.sun.org.apache.xpath.internal.operations.String;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/*
* 1、我们自定义一个Handler需要继承netty规定好的某个 HandlerAdapter
* */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /*
    * 读取数据（读取客户端发送的消息）
    * ChannelHandlerContext ：上下文对象，含有管道pipeline，通道channel，地址
    * Object msg：客户端发送的数据
    * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("当前线程名称："+Thread.currentThread().getName());
//        System.out.println("server ctx= "+ctx);

        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链表，出栈入栈

        //将msg转成一个ByteBuf
        //ByteBuf 是 Netty 提供的，不是 NIO的 ByteBuffer
        ByteBuf byteBuf= (ByteBuf) msg;
        System.out.println("客户端发送消息是："+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+ctx.channel().remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //write + flush ，数据写入到缓存并刷新
        //对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    //处理异常，一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
