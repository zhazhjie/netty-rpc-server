package com.zzj.core;

import com.zzj.service.UserService;
import entity.User;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class ProxyFactory {
    public static <T> T build(Class<T> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            ChannelConfig.ctx.writeAndFlush(Unpooled.copiedBuffer("1", CharsetUtil.UTF_8));
//            countDownLatch.await();
//            return result;
            return "";
        });
        return (T) enhancer.create();
    }
}
