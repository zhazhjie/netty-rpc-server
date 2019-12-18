package com.zzj.core.common.codec;

import com.alibaba.fastjson.JSON;
import com.zzj.core.common.entity.ReqData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RequestEncoder extends MessageToByteEncoder<ReqData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ReqData msg, ByteBuf out) {
        String jsonData = JSON.toJSONString(msg);
        out.writeInt(jsonData.length());
        out.writeBytes(jsonData.getBytes());
    }
}
