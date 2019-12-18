package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.entity.ReqData;
import com.zzj.entity.RespData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class RequestDecoder extends MessageToMessageDecoder<ReqData> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ReqData reqData, List<Object> list) {
        Object[] args = reqData.getArgs();
        Class[] argsType = reqData.getArgsType();
        try {
            Object[] realArgs = JSON.parseArray(JSON.toJSONString(args), argsType).toArray();
            reqData.setArgs(realArgs);
            list.add(reqData);
        }catch (Exception e){
            RespData respData = new RespData();
            respData.setId(reqData.getId());
            respData.setSuccess(false);
            respData.setMsg("invalid parameters");
            ctx.writeAndFlush(respData);
        }
    }
}