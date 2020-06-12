package com.netty.rpc.api;

//接口，服务提供方和消费方共享
public interface TestService {
    String test(String message);
}
