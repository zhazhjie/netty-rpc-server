package com.zzj.core;

import com.alibaba.fastjson.JSON;
import entity.RpcData;
import entity.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

public class ServerHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.print(msg);
        RpcData rpcData =  JSON.parseObject(((ByteBuf)msg).toString(CharsetUtil.UTF_8), RpcData.class);;
        Class targetType = rpcData.getTargetType();
        Object o = new UserServiceImpl();
        Object[] args = rpcData.getArgs();
        Class[] objects = Arrays.stream(args).map(v -> v.getClass()).toArray(Class[]::new);
        Method method = targetType.getMethod(rpcData.getMethodName(), objects);
        Object invoke = method.invoke(o, args);

//        ByteBuf byteBuf = (ByteBuf) msg;
//        String name = byteBuf.toString(CharsetUtil.UTF_8);
//        System.out.print("name:"+name);
        rpcData.setResult(invoke);
        ctx.write(Unpooled.copiedBuffer(JSON.toJSONString(rpcData),CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}