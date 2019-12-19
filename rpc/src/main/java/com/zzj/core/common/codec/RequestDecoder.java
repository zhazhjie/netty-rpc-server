package com.zzj.core.common.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzj.core.common.constant.ErrorMsg;
import com.zzj.core.common.entity.ReqData;
import com.zzj.core.common.entity.RespData;
import com.zzj.core.common.exception.RpcException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.StringUtil;

import java.util.List;

/**
 * server入站
 * 请求解码器
 *  byte[] to ReqData
 */
public class RequestDecoder extends MessageToMessageDecoder<byte[]> {
    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] bytes, List<Object> list) {
        ReqData reqData=new ReqData();
        try {
            reqData = JSONObject.parseObject(bytes, ReqData.class);
            Object[] args = reqData.getArgs();
            //验证必要参数
            if(reqData.getId()==null||reqData.getTargetType()==null|| StringUtil.isNullOrEmpty(reqData.getMethodName())){
                throw new RpcException(ErrorMsg.INVALID_PARAMS);
            }
            Class[] argsType = reqData.getArgsType();
            //参数类型转换
            Object[] realArgs = JSON.parseArray(JSON.toJSONString(args), argsType).toArray();
            reqData.setArgs(realArgs);
            list.add(reqData);
            //失败抛异常或响应失败结果？
        }catch (Exception e){
            RespData respData = new RespData();
            respData.setId(reqData.getId());
            respData.setSuccess(false);
            respData.setMsg(ErrorMsg.INVALID_PARAMS);
            ctx.writeAndFlush(respData);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}