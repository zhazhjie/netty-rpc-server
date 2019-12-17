package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.entity.ReqData;
import com.zzj.entity.RespData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info(msg.toString());
        ReqData reqData = (ReqData)msg;
        Class targetType = reqData.getTargetType();
        //TODO 实例来源
        Object o = new UserServiceImpl();
        RespData respData = new RespData();
        respData.setId(reqData.getId());
        try {
            Method method = targetType.getMethod(reqData.getMethodName(), reqData.getArgsType());
            Object invoke = method.invoke(o, reqData.getArgs());
            respData.setResult(JSON.toJSONString(invoke));
            respData.setResultType(invoke == null ? null : invoke.getClass());
        } catch (Exception e) {
            respData.setSuccess(false);
            respData.setMsg(e.getMessage());
        }
        ctx.writeAndFlush(respData);
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}