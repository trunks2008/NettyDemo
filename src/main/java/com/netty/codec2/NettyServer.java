package com.netty.codec2;

import com.netty.codec.StudentPoJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        /*
        *创建 bossGroup 和 workerGroup
        * bossGroup只是处理连接的请求 ，真正和客户端的业务处理交给 workerGroup
        * 两个都是无限循环
        * bossGroup 和 workerGroup含有的子线程的个数是怎么确认的？
        * 默认是 cpu核数*2
        * */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用 NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    //.handler(null) //bossGroup 对应的 handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象（匿名对象）
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            System.out.println("客户的SocketChannel:"+ch.hashCode());
                            //制定对那种对象进行解码
                            ch.pipeline().addLast("decoder",new ProtobufDecoder(MyDataInfo.MyMessage.getDefaultInstance()));
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); //给我们的 workerGroup的 EventLoop 对应的管道设置处理器
            System.out.println("服务器 IS READY");

            //绑定一个端口并且同步，生成一个ChannelFuture对象
            //启动服务器
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //给cf注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()){
                        System.out.println("bind success");
                    }else
                        System.out.println("bind failed");
                }
            });

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
