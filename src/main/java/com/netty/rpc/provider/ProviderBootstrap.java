package com.netty.rpc.provider;

import com.netty.rpc.netty.NettyServer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

//启动 NettyServer
public class ProviderBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",7000);
    }
}
