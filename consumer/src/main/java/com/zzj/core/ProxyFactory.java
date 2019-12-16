package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.service.UserService;
import entity.RpcData;
import entity.User;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

public class ProxyFactory {
    public static <T> T build(Class<T> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            Long id=System.currentTimeMillis();
            RpcData rpcData = new RpcData();
            rpcData.setArgs(objects);
            rpcData.setId(id);
            rpcData.setMethodName(method.getName());
            rpcData.setTargetType(clazz);
//            CountDownLatch countDownLatch = new CountDownLatch(1);
//            ChannelConfig.countDownLatchMap.put(System.currentTimeMillis(),countDownLatch);
            ChannelConfig.ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcData), CharsetUtil.UTF_8));
//            countDownLatch.await();
            return ChannelConfig.resultMap.get(id);
        });
        return (T) enhancer.create();
    }
}
