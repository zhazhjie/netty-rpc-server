package com.zzj.core.consumer;

import com.zzj.core.common.constant.ErrorMsg;
import com.zzj.core.common.entity.RespData;
import com.zzj.core.common.exception.RpcException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 响应结果最终处理
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<RespData> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelConfig.ctx = ctx;
//        ChannelConfig.ctx.writeAndFlush(new ReqData());
//        ChannelConfig.ctx.writeAndFlush(new ReqData());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RespData respData) {
        //验证是否有对应的requestId
        if (ChannelConfig.countDownLatchMap.get(respData.getId()) == null) {
            throw new RpcException(ErrorMsg.INVALID_RESULT);
        }
        ChannelConfig.successCallback(respData);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.print(1);
        cause.printStackTrace();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
