package com.zzj.core;

import com.zzj.entity.ReqData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class CheckReqDecoder extends MessageToMessageDecoder<ReqData> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ReqData msg, List<Object> out) {

    }
}
