package com.netty.boundhandler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * decode 方法会根据接收的数据，被调用多次，知道确定没有新的元素被添加到List，或者是ByteBuf没有更多的可读字节为止
     * 如果list out不为空，就会将list的内容传递给下一个channellInboundhandler处理，该处理器的方法也会被调用多次
     * @param ctx 上下文
     * @param in 入栈数据
     * @param out List集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
        System.out.println("MyServerByteToLongDecoder decode 被调用");
        //Long 8 个字节，需要判断有8个字节，才能读取一个Long
        if(in.readableBytes()>=8){
            out.add(in.readLong());
        }
    }
}
