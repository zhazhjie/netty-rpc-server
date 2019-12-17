package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzj.entity.ReqData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class RequestDecoder extends LengthFieldBasedFrameDecoder {
    public RequestDecoder(){
        super(64*1024,0,4);
    }
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        int expectLength = in.readInt();
//        int realLength=in.readableBytes();
//        if(realLength!=expectLength){
//            RespData respData = new RespData();
//            respData.setSuccess(false);
//            respData.setMsg("incomplete data");
//            ctx.writeAndFlush(respData);
//            return null;
//        }
        byte[] bytes = new byte[expectLength];
        in.readBytes(bytes);
        ReqData reqData = JSONObject.parseObject(bytes, ReqData.class);
        Object[] args = reqData.getArgs();
        Class[] argsType = reqData.getArgsType();
        Object[] realArgs = JSON.parseArray(JSON.toJSONString(args), argsType).toArray();
        reqData.setArgs(realArgs);
        return reqData;
    }
}