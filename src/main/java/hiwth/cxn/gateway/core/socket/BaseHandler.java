package hiwth.cxn.gateway.core.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, T request) throws Exception {
        sessionHandle(channelHandlerContext, request);
    }

    protected abstract void sessionHandle(ChannelHandlerContext context, T request);
}
