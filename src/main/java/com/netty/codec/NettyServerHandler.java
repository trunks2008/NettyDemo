package com.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/*
* 1、我们自定义一个Handler需要继承netty规定好的某个 HandlerAdapter
* */
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPoJO.Student> {

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        //读取客户端发送的pojo
//        StudentPoJO.Student student= (StudentPoJO.Student) msg;
//        System.out.println("客户端发送的数据  id="+student.getId()+"  name="+student.getName());
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, StudentPoJO.Student msg) throws Exception {
        System.out.println("客户端发送的数据  id="+msg.getId()+"  name="+msg.getName());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    //处理异常，一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
