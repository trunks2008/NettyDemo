package com.netty.boundhandler;

import com.netty.boundhandler.codec.MyByteToLongDecoder;
import com.netty.boundhandler.codec.MyByteToLongDecoder2;
import com.netty.boundhandler.codec.MyLongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //入栈的handler进行解码
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());

        pipeline.addLast(new MyLongToByteEncoder());

        pipeline.addLast(new MyServerHandler());
    }
}
