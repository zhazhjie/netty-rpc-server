package com.zzj.core;

import com.alibaba.fastjson.JSON;
import com.zzj.entity.RespData;
import com.zzj.exception.RpcException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelConfig.ctx = ctx;
        log.info("启动成功");
//        Thread thread = new Thread(() -> {
//            UserService userService = ProxyFactory.build(UserService.class);
//            User userById = userService.getUserById(1L);
//            log.info("result:"+userById);
//        });
//        thread.start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        String jsonStr = byteBuf.toString(CharsetUtil.UTF_8);
        RespData respData = JSON.parseObject(jsonStr, RespData.class);
        if(respData.isSuccess()){
            Object result = respData.getResultType() == null ? null : JSON.parseObject(respData.getResult(), respData.getResultType());
            ChannelConfig.setResult(respData.getId(), result);
        }else{
            ChannelConfig.setResult(respData.getId(), new RpcException(respData.getMsg()));
        }
        log.info(jsonStr);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        log.info("remove");
    }
}
