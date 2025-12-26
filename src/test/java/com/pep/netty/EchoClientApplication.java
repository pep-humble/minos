package com.pep.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * echo 客户端
 *
 * @author liu.gang
 */
@Slf4j
public class EchoClientApplication {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new EchoClientChannelHandler());
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect("localhost", 8188).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            worker.shutdownGracefully();
        }
    }

}
