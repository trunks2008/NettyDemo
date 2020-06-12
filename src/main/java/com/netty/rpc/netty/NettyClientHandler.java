package com.netty.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;

    //返回的结果
    private String result;

    //客户端调用方法时，传入的参数
    private String param;

    //与服务器的连接创建后，就会被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //其他方法会用到这个context
        context = ctx;
    }

    //收到服务器的数据后调用
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        //唤醒等待线程
        notify();
    }

    //被代理对象调用，发送数据给服务器
    // 等待被channelRead 方法唤醒
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        wait();
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void setParam(String param) {
        this.param = param;
    }
}
