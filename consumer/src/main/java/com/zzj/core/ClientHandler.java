package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.service.UserService;
import entity.RpcData;
import entity.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;

public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelConfig.ctx=ctx;
        Thread thread = new Thread(() -> {
            UserService userService = ProxyFactory.build(UserService.class);
            User userById = userService.getUserById(12345678932145L);
        });
        thread.start();
//        ctx.writeAndFlush(Unpooled.copiedBuffer("666",CharsetUtil.UTF_8));
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(UserService.class);
//        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
//            Long id=System.currentTimeMillis();
//            RpcData rpcData = new RpcData();
//            rpcData.setArgs(objects);
//            rpcData.setId(id);
//            rpcData.setMethodName(method.getName());
//            rpcData.setTargetType(UserService.class);
////            CountDownLatch countDownLatch = new CountDownLatch(1);
////            ChannelConfig.countDownLatchMap.put(System.currentTimeMillis(),countDownLatch);
//            ChannelConfig.ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcData), CharsetUtil.UTF_8));
//            ChannelConfig.ctx.writeAndFlush(Unpooled.copiedBuffer(JSON.toJSONString(rpcData), CharsetUtil.UTF_8));
////            countDownLatch.await();
//            return "666";
//        });
//        UserService userService = (UserService) enhancer.create();
//        userService.sayHi("a");
//        ChannelConfig.ctx.writeAndFlush(Unpooled.copiedBuffer("666", CharsetUtil.UTF_8));
//        ChannelConfig.ctx.writeAndFlush(Unpooled.copiedBuffer("777", CharsetUtil.UTF_8));
//        ChannelConfig.ctx.writeAndFlush(Unpooled.copiedBuffer("888", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        String jsonStr = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.print(jsonStr);
    }
}
