package com.zzj.core.consumer;

import com.zzj.core.common.entity.RespData;
import com.zzj.core.common.exception.RpcException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<RespData> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelConfig.ctx = ctx;
//        log.info("启动成功");
//        Thread thread = new Thread(() -> {
//            UserService userService = ProxyFactory.build(UserService.class);
//            User userById = userService.getUserById(1L);
//        });
//        thread.start();

//        ChannelConfig.ctx.writeAndFlush(new ReqData());
//        ChannelConfig.ctx.writeAndFlush(new ReqData());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RespData respData) {
        if(respData.isSuccess()){
            ChannelConfig.setResult(respData.getId(), respData.getResult());
        }else{
            throw new RpcException(respData.getMsg());
        }
        log.info(respData.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
//        log.info("remove");
    }
}
