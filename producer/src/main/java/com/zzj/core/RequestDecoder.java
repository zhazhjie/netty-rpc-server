package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.entity.RpcData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class RequestDecoder extends ByteToMessageDecoder {
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in,
                       List<Object> out) throws Exception {
        if(in.readableBytes()>0){
            RpcData rpcData = JSON.parseObject(in.toString(CharsetUtil.UTF_8), RpcData.class);
            out.add(rpcData);
        }
    }
}