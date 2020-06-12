package com.netty.rpc.consumer;

import com.netty.rpc.api.TestService;
import com.netty.rpc.api.Protocol;
import com.netty.rpc.netty.NettyClient;

public class ConsumerBootstrap {

    public static void main(String[] args) {
        NettyClient consumer = new NettyClient();
        TestService proxy =(TestService) consumer.getProxy(TestService.class, Protocol.HEADER);

        String result = proxy.test("hi,i am client");
        System.out.println("result: "+result);
    }
}
