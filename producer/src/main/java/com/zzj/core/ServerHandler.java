package com.zzj.core;

import com.alibaba.fastjson.JSON;
import entity.ProtocolData;
import entity.User;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ServerHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.print(msg);
//        ByteBuf byteBuf = (ByteBuf) msg;
//        String name = byteBuf.toString(CharsetUtil.UTF_8);
//        System.out.print("name:"+name);
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.getUserById(1L);
        ProtocolData protocolData = new ProtocolData();
        protocolData.setResultType(User.class);
        protocolData.setResult(user);
        ctx.write(Unpooled.copiedBuffer(JSON.toJSONString(protocolData),CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
