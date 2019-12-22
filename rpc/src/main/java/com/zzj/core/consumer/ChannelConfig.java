package com.zzj.core.consumer;

import com.zzj.core.common.entity.RespData;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.*;

public class ChannelConfig {
    //通道上下文
    static ChannelHandlerContext ctx;
    //请求CountDownLatch暂存处
    static Map<Long, CountDownLatch> countDownLatchMap = new ConcurrentHashMap<>();
    //响应结果暂存处
    static Map<Long, RespData> resultMap = new ConcurrentHashMap<>();
    //请求超时时间
    static long timeout = 10L;

    static void successCallback(RespData respData) {
        resultMap.put(respData.getId(), respData);
        CountDownLatch countDownLatch = countDownLatchMap.remove(respData.getId());
        countDownLatch.countDown();
    }

    public void initThreadPool() {
        new ThreadPoolExecutor(8, 16, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }
}
