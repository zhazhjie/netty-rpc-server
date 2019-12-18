package com.zzj.core.consumer;


import com.zzj.core.common.constant.ErrorMsg;
import com.zzj.core.common.entity.ReqData;
import com.zzj.core.common.exception.TimeoutException;
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
            ChannelConfig.ctx.writeAndFlush(reqData);
            boolean await = countDownLatch.await(10L, TimeUnit.SECONDS);
            if (!await) {
                throw new TimeoutException(ErrorMsg.TIME_OUT);
            }
            return ChannelConfig.resultMap.get(id);
        });
        return (T) enhancer.create();
    }
}
