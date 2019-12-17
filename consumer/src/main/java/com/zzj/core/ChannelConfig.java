package com.zzj.core;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Data
public class ChannelConfig {
    public static ChannelHandlerContext ctx;
    public static Map<Long, CountDownLatch> countDownLatchMap = new HashMap<>();
    public static Map<Long, Object> resultMap = new HashMap<>();

    public static void setResult(Long requestId, Object result) {
        resultMap.put(requestId, result);
        CountDownLatch countDownLatch = countDownLatchMap.remove(requestId);
        countDownLatch.countDown();
    }

    public void initThreadPool() {
        new ThreadPoolExecutor(8, 16, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }
}
