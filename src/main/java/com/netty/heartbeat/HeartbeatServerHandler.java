package com.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
     if(evt instanceof IdleStateEvent){
         //将evt向下转型
         IdleStateEvent event= (IdleStateEvent) evt;
         String eventType=null;
         switch (event.state()){
             case READER_IDLE:
                 eventType="读空闲";
                 break;
             case WRITER_IDLE:
                 eventType="写空闲";
                 break;
             case ALL_IDLE:
                 eventType="读写空闲";
                 break;
             default:
                 eventType="不空闲";
         }
         System.out.println(ctx.channel().remoteAddress()+" 超时: "+eventType);
         System.out.println("相应处理");
         //如果发生空闲，我们关闭通道，那么只会检测一次心跳
         ctx.channel().close();
     }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
