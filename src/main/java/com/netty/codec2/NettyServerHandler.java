package com.netty.codec2;

import com.netty.codec.StudentPoJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/*
* 1、我们自定义一个Handler需要继承netty规定好的某个 HandlerAdapter
* */
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        //根据data_type显示不同信息
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if(dataType==MyDataInfo.MyMessage.DataType.StudentType){
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("学生id="+student.getId()+" 学生名字="+student.getName());
        }else if(dataType==MyDataInfo.MyMessage.DataType.WorkerType){
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("工人名字="+worker.getName()+" 工人年龄="+worker.getAge());
        }else {
            System.out.println("传输的类型不正确");
        }
    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, StudentPoJO.Student msg) throws Exception {
//        System.out.println("客户端发送的数据  id="+msg.getId()+"  name="+msg.getName());
//    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端",CharsetUtil.UTF_8));
    }

    //处理异常，一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
