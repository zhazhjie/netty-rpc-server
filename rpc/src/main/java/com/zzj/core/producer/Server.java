package com.zzj.core.producer;

import com.zzj.core.common.codec.LengthFrameDecoder;
import com.zzj.core.common.codec.RequestDecoder;
import com.zzj.core.common.codec.ResponseEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Server {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws Exception {
        new Server().start(8888);
    }

    public void start(int port) {
        executor.execute(() -> {
            NioEventLoopGroup group = new NioEventLoopGroup();
            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(group)
                        .channel(NioServerSocketChannel.class)
                        .localAddress(new InetSocketAddress(port))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) {
                                socketChannel.pipeline()
                                        .addLast(new ResponseEncoder())
                                        .addLast(new LengthFrameDecoder(64 * 1024, 0, 4))
                                        .addLast(new RequestDecoder())
                                        .addLast(new ServerHandler())
                                ;
                            }
                        });
                ChannelFuture channelFuture = serverBootstrap.bind().sync();
                log.info("Server start on port {}", port);
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        });
    }
}
