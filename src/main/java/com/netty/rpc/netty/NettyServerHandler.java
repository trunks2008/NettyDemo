package com.netty.rpc.netty;

import com.netty.rpc.api.Protocol;
import com.netty.rpc.provider.TestServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

//服务端handler
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息，并调用服务
        System.out.println("msg="+msg);
        // 客户端在调用服务器的api时，需要定义一个协议
        // 比如我们要求每发消息时，都必须以某个字符串开头 "HelloServer#hello"
        if(msg.toString().startsWith(Protocol.HEADER)){
            String result = new TestServiceImpl().test(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
