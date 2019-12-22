package com.zzj.core.common.codec;

import com.alibaba.fastjson.JSON;
import com.zzj.core.common.entity.ReqData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * client出站
 * 请求编码器
 * ReqData to byte[]
 * (int)length+(json)body
 */
public class RequestEncoder extends MessageToByteEncoder<ReqData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ReqData msg, ByteBuf out) {
        String jsonData = JSON.toJSONString(msg);
        out.writeInt(jsonData.length());
        out.writeBytes(jsonData.getBytes());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
