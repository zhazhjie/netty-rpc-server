package com.zzj.core.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 入站口
 * 报文解码器
 * 基于长度的协议
 * (int)length+(json)body
 */
public class LengthFrameDecoder extends LengthFieldBasedFrameDecoder {
    public LengthFrameDecoder(int maxFrameLength,int lengthFieldOffset,int lengthFieldLength){
        super(maxFrameLength,lengthFieldOffset,lengthFieldLength);
    }
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        int expectLength = in.readInt();
        byte[] bytes = new byte[expectLength];
        in.readBytes(bytes);
        return bytes;
    }


}