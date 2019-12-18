package com.zzj.core.producer;

import com.zzj.core.common.constant.ErrorMsg;
import com.zzj.core.common.entity.ReqData;
import com.zzj.core.common.entity.RespData;
import com.zzj.core.common.exception.RpcException;
import com.zzj.core.common.utils.BeanFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info(msg.toString());
        ReqData reqData = (ReqData)msg;
        Class targetType = reqData.getTargetType();
        RespData respData = new RespData();
        respData.setId(reqData.getId());
        try {
            Object o = BeanFactory.getBean(reqData.getTargetType());
            if(o==null){
                throw new RpcException(ErrorMsg.CLASS_NOT_FOUND);
            }
            Method method = targetType.getMethod(reqData.getMethodName(), reqData.getArgsType());
            Object invoke = method.invoke(o, reqData.getArgs());
            respData.setResult(invoke);
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
    }
}