package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzj.entity.ReqData;
import com.zzj.entity.RespData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class LengthFrameDecoder extends LengthFieldBasedFrameDecoder {
    public LengthFrameDecoder(){
        super(64*1024,0,4);
    }
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        int expectLength = in.readInt();
        int realLength=in.readableBytes();
        byte[] bytes = new byte[expectLength];
        in.readBytes(bytes);
        return JSONObject.<ReqData>parseObject(bytes, ReqData.class);
    }
}