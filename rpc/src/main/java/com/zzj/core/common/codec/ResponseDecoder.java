package com.zzj.core.common.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzj.core.common.constant.ErrorMsg;
import com.zzj.core.common.entity.RespData;
import com.zzj.core.common.exception.RpcException;
import com.zzj.core.consumer.ChannelConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * client入站
 * 响应解码器
 * byte[] to RespData
 */
public class ResponseDecoder extends MessageToMessageDecoder<byte[]> {
    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] bytes, List<Object> list) {
        RespData respData = JSONObject.parseObject(bytes, RespData.class);
        if (respData.getId() == null) {
            throw new RpcException(ErrorMsg.INVALID_RESULT);
        }
        Object result = respData.getResultType() == null ? null : JSON.parseObject(JSONObject.toJSONString(respData.getResult()), respData.getResultType());
        respData.setResult(result);
        list.add(respData);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
