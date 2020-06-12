package com.netty.rpc.provider;

import com.netty.rpc.api.TestService;

public class TestServiceImpl implements TestService {
    @Override
    public String test(String message) {
        System.out.println("Server has received:"+ message);
        if (message !=null){
            return "hi client, Server has Received:["+ message+"]";
        }else{
            return "empty message";
        }
    }
}
