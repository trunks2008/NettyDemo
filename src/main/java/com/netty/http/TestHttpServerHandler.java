package com.netty.http;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter的 子类
 * HttpObject：客户端和服务端通讯的数据被封装成 HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是不是一个httpRequest请求
        if (msg instanceof HttpRequest){
            System.out.println(ctx.pipeline().hashCode() +"   "+this.hashCode());
//            System.out.println("msg 类型:"+msg.getClass());
            System.out.println("客户端地址："+ctx.channel().remoteAddress());

            HttpRequest httpRequest= (HttpRequest) msg;
            //获取URI，过滤指定访问
            URI uri =new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求图标，不作响应");
                return;
            }

            //回复信息给浏览器 [http协议]
            ByteBuf content= Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);

            //构造一个http响应，即httpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);

            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            //将构建好的response返回
            ctx.writeAndFlush(response);
        }

    }
}
