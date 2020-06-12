package com.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", Charset.forName("UTF-8"));

        if (byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            System.out.println(new String(content,Charset.forName("UTF-8")));
            System.out.println(byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());

            byteBuf.readByte();
            System.out.println(byteBuf.readableBytes());

            System.out.println(byteBuf.getCharSequence(0, 4, Charset.forName("utf-8")));
        }
    }
}
