package com.zzj.core;

import com.zzj.service.UserService;
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
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private String result;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        new Thread(()->{
//            Enhancer enhancer = new Enhancer();
//            enhancer.setSuperclass(UserService.class);
//            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
//                User user = new User();
//                user.setId(1L);
//                ctx.writeAndFlush(user);
////                countDownLatch.await();
//                return result;
//            });
//            UserService userService = (UserService) enhancer.create();
//            String jack = userService.sayHi("jack");
//            System.out.println(jack);
//        }).start();
        User user = new User();
        user.setId(1L);
        ByteBuf buf = Unpooled.buffer(32);
        buf.writeLong(user.getId());
        byte[] array = buf.array();
        ctx.writeAndFlush(Unpooled.copiedBuffer(array));

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        String str = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("result:"+ str);
        result=str;
//        countDownLatch.countDown();
    }
}
