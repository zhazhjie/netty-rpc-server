package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.entity.ReqData;
import com.zzj.entity.RespData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.print(msg);
        String byteStr = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
        ReqData reqData = JSON.parseObject(byteStr, ReqData.class);
        Class targetType = reqData.getTargetType();
        //TODO 实例来源
        Object o = new UserServiceImpl();
        Object[] args = reqData.getArgs();
        Class[] argsType = reqData.getArgsType();
        Object[] realArgs = JSON.parseArray(JSON.toJSONString(args), argsType).toArray();
        RespData respData = new RespData();
        respData.setId(reqData.getId());
        try {
            Method method = targetType.getMethod(reqData.getMethodName(), argsType);
            Object invoke = method.invoke(o, realArgs);
            respData.setResult(JSON.toJSONString(invoke));
            respData.setResultType(invoke == null ? null : invoke.getClass());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            respData.setSuccess(false);
            respData.setMsg(e.getMessage());
        }
        ctx.write(Unpooled.copiedBuffer(JSON.toJSONString(respData), CharsetUtil.UTF_8));
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