package hiwth.cxn.gateway.core.socket;

import hiwth.cxn.gateway.core.socket.handler.AuthorizationHandler;
import hiwth.cxn.gateway.core.socket.handler.GatewayServerHandler;
import hiwth.cxn.gateway.core.socket.handler.ProtocolDataHandler;
import hiwth.cxn.gateway.core.session.Configuration;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class SocketChannelInitializer extends ChannelInitializer {

    private Configuration configuration;

    public SocketChannelInitializer(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new GatewayServerHandler(configuration));
        pipeline.addLast(new AuthorizationHandler(configuration));
        pipeline.addLast(new ProtocolDataHandler(configuration));

    }
}
