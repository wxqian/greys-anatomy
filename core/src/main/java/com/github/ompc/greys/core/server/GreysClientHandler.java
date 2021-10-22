package com.github.ompc.greys.core.server;

import fx.greys.fork.common.entity.GreysMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GreysClientHandler extends SimpleChannelInboundHandler<GreysMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GreysMessage greysMessage) throws Exception {
        
    }
}
