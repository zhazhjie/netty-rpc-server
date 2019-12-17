package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.entity.RespData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseEncoder extends MessageToByteEncoder<RespData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RespData msg, ByteBuf out) {
        System.out.println(msg);
        out.writeBytes(JSON.toJSONString(msg).getBytes());
    }
}