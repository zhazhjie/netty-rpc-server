package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.entity.ReqData;
import com.zzj.exception.RpcException;
import com.zzj.exception.TimeoutException;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ProxyFactory {
    public static <T> T build(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            Long id = System.currentTimeMillis();
            ReqData reqData = new ReqData();
            reqData.setArgs(objects);
            reqData.setId(id);
            reqData.setMethodName(method.getName());
            reqData.setTargetType(clazz);
            Class[] argsType = Arrays.stream(objects).map(v -> v == null ? null : v.getClass()).toArray(Class[]::new);
            reqData.setArgsType(argsType);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ChannelConfig.countDownLatchMap.put(id, countDownLatch);
            ChannelConfig.ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(reqData), CharsetUtil.UTF_8));
            boolean await = countDownLatch.await(10L, TimeUnit.SECONDS);
            if (!await) {
                throw new TimeoutException("call producer timeout");
            }
            Object result = ChannelConfig.resultMap.get(id);
            if(result instanceof Exception){
                throw (Exception)result;
            }
            return result;
        });
        return (T) enhancer.create();
    }
}
