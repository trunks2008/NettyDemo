package com.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {
    public static void main(String[] args) {

        //创建一个ByteBuf
        //1、创建对象，该对象包含一个数组arr，是一个byte[10]
        //2、底层维护了readIndex和 writeIndex， 因此和nio不同，不需要flip反转
        //3、通过readIndex和 writeIndex 和capacity，将buffer分成三个区域，
        //0---readIndex 已经读过的数据
        //readIndex --- writeIndex， 可读区域
        // writeIndex ---capacity，可写区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        System.out.println(buffer.capacity());
        //读取方法1
//        for (int i = 0; i <buffer.capacity() ; i++) {
//            System.out.println(buffer.getByte(i));
//        }
        //读取方法2
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
    }
}
