package com.netty.boundhandler;

import com.netty.boundhandler.codec.MyByteToLongDecoder;
import com.netty.boundhandler.codec.MyByteToLongDecoder2;
import com.netty.boundhandler.codec.MyLongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个出栈的handler，对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());

//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());

        //处理业务逻辑
        pipeline.addLast(new MyClientHandler());
    }
}
