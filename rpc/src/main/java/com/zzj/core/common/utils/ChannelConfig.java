package com.zzj.core.common.utils;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ChannelConfig {
    //通道上下文
    public static ChannelHandlerContext ctx;
    //请求CountDownLatch暂存处
    public static Map<Long, CountDownLatch> countDownLatchMap = new HashMap<>();
    //响应结果暂存处
    public static Map<Long, Object> resultMap = new HashMap<>();
    //请求超时时间
    public static long timeout=10L;

    public static void setResult(Long requestId, Object result) {
        resultMap.put(requestId, result);
        CountDownLatch countDownLatch = countDownLatchMap.remove(requestId);
        countDownLatch.countDown();
    }

    public void initThreadPool() {
        new ThreadPoolExecutor(8, 16, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }
}
