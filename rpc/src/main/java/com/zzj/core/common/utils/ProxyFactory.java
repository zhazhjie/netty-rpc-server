package com.zzj.core.common.utils;


import com.zzj.core.common.constant.ErrorMsg;
import com.zzj.core.common.entity.ReqData;
import com.zzj.core.common.exception.TimeoutException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 代理工厂
 * 生成cglib代理对象
 */
public class ProxyFactory {
    public static <T> T build(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            //请求和响应携带同一个id;
            Long id = IdWorker.getId();
            ReqData reqData = new ReqData();
            reqData.setArgs(objects);
            reqData.setId(id);
            reqData.setMethodName(method.getName());
            reqData.setTargetType(clazz);
            Class[] argsType = Arrays.stream(objects).map(v -> v == null ? null : v.getClass()).toArray(Class[]::new);
            reqData.setArgsType(argsType);
            CountDownLatch countDownLatch = new CountDownLatch(1);
            //暂存CountDownLatch，等待响应触发countDown()
            ChannelConfig.countDownLatchMap.put(id, countDownLatch);
            //发送请求
            ChannelConfig.ctx.writeAndFlush(reqData);
            //CountDownLatch阻塞线程
            boolean await = countDownLatch.await(ChannelConfig.timeout, TimeUnit.SECONDS);
            if (!await) {
                //超时移除，并抛TimeoutException异常
                ChannelConfig.countDownLatchMap.remove(id);
                throw new TimeoutException(ErrorMsg.TIME_OUT);
            }
            //响应结果，并从map中移除
            return ChannelConfig.resultMap.remove(id);
        });
        return (T) enhancer.create();
    }
}
