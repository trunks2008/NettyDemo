package com.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个netty提供的 httpServerCodec， codec=[coder,decoder]
        //1、是netty提供的处理http的编解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());

        //2、增加自己的处理器
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
        System.out.println("ok");
    }
}
