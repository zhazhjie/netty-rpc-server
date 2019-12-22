package com.zzj.core.consumer;

import com.zzj.core.common.codec.LengthFrameDecoder;
import com.zzj.core.common.codec.RequestEncoder;
import com.zzj.core.common.codec.ResponseDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Client {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private Timer timer;

    public static void main(String[] args) throws Exception {
        new Client().start("127.0.0.1", 8888);
    }

    public Client() {
    }

    public Client(long timeout) {
        if (timeout <= 0) {
            throw new IllegalArgumentException("timeout must be greater than zero!");
        }
        ChannelConfig.timeout = timeout;
    }

    public void start(String hostname, int port) {
        executor.execute(() -> {
            NioEventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .remoteAddress(new InetSocketAddress(hostname, port))
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) {
                                socketChannel.pipeline()
                                        .addLast(new LengthFrameDecoder(64 * 1024, 0, 4))
                                        .addLast(new ResponseDecoder())
                                        .addLast(new RequestEncoder())
                                        .addLast(new ClientHandler())
                                ;
                            }
                        });
                ChannelFuture channelFuture = bootstrap.connect().sync();
                log.info("Connect to server {}:{} success", hostname, port);
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
                if (timer == null) {
                    timer = new Timer();
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        start(hostname, port);
                    }
                }, 5000);
            } finally {
                group.shutdownGracefully();
            }
        });
    }
}
