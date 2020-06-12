package com.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .handler(new LoggingHandler(LogLevel.INFO))//在bossGroup增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            // 因为基于http协议，使用http的编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            //是以块方式写，添加chunkedWrite处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            /**
                             * 1、http的数据在传输过程中是分段的，HttpObjectAggregator 可以将多个段聚合
                             * 2、这就是为什么，当浏览器发送大量数据时，就会发出多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 1、对于 websocket，它的数据是以帧（frame） 的形式处理
                             * 2、可以看到 WebSocketFrame 下面有6个子类
                             * 3、浏览器请求时，请求的uri：  ws://localhost:7000/xxx
                             * 4、WebSocketServerProtocolHandler 核心功能：将http协议升级为ws协议，保持长连接
                             * 5、协议升级是通过一个 状态码 101 ，来实现的
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            //自定义handler，处理业务逻辑
                            pipeline.addLast(new WebSocketServerTextFrameHandler());
                        }
                    });
            System.out.println("netty 服务器 IS READY");
            ChannelFuture channelFuture = bootstrap.bind(9000).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
