package com.github.ompc.greys.core.server;

import com.github.ompc.greys.core.Configure;
import fx.greys.fork.common.netty.MessageDecoder;
import fx.greys.fork.common.netty.MessageEncoder;
import fx.greys.fork.common.util.LogUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;

import java.lang.instrument.Instrumentation;

public class WebGreyServer {

    private final Logger logger = LogUtil.getLogger();
    private final Bootstrap bootstrap = new Bootstrap();

    private final Thread jvmShutdownHooker = new Thread("ga-shutdown-hooker") {

        @Override
        public void run() {
            WebGreyServer.this._destroy();
        }
    };

    public WebGreyServer(Instrumentation inst, Configure configure) {
        initBootStrap(configure);
    }

    private void initBootStrap(Configure configure) {
        NioEventLoopGroup nioEventLoop = new NioEventLoopGroup();
        bootstrap.group(nioEventLoop).channel(NioSocketChannel.class).
                option(ChannelOption.TCP_NODELAY, true).
                option(ChannelOption.SO_REUSEADDR, true).
                option(ChannelOption.SO_KEEPALIVE, true).
                handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(1800, 0, 0))
                                .addLast(new MessageDecoder()).addLast(new MessageEncoder()).addLast(new GreysClientHandler());
                    }
                });
    }

    private void _destroy() {
    }
}
