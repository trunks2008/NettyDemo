package com.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收到数据，并处理
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务端接收到信息如下: len="+len+"  content="+new String(content,CharsetUtil.UTF_8));
        System.out.println("服务器接收到消息数量:"+(++count));

        //回复的消息
        String responceContent = UUID.randomUUID().toString();

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responceContent.getBytes("utf-8").length);
        messageProtocol.setContent(responceContent.getBytes("utf-8"));
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
        ctx.close();
    }
}
