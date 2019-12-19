package com.zzj.core.consumer;

import com.zzj.core.common.entity.RespData;
import com.zzj.core.common.exception.RpcException;
import com.zzj.core.common.utils.ChannelConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 响应结果最终处理
 */
public class ClientHandler extends SimpleChannelInboundHandler<RespData> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelConfig.ctx = ctx;
//        ChannelConfig.ctx.writeAndFlush(new ReqData());
//        ChannelConfig.ctx.writeAndFlush(new ReqData());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RespData respData) {
        if(respData.isSuccess()){
            //成功响应，触发countDown();
            ChannelConfig.setResult(respData.getId(), respData.getResult());
        }else{
            throw new RpcException(respData.getMsg());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
