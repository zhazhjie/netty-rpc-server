package com.zzj.core;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Data
public class ChannelConfig {
    public static ChannelHandlerContext ctx;
    public static Map<Class, CountDownLatch> countDownLatchMap=new HashMap<>();
}