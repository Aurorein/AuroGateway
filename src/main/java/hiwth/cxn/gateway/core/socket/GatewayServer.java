package hiwth.cxn.gateway.core.socket;

import hiwth.cxn.gateway.core.session.Configuration;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;


public class GatewayServer implements Callable {
    private final Logger logger = LoggerFactory.getLogger(GatewayServer.class);

    private Configuration configuration;

    private final EventLoopGroup boss = new NioEventLoopGroup(1);

    private final EventLoopGroup work = new NioEventLoopGroup();

    public GatewayServer(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Channel call() throws Exception {
        ChannelFuture channelFuture = null;

        try{
            ServerBootstrap b = new ServerBootstrap();

            b.group(boss, work)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SocketChannelInitializer(configuration));
            channelFuture = b.bind(new InetSocketAddress(2889)).sync();
        } catch(Exception e) {
            logger.error("出错了!");
        } finally {
            if(channelFuture != null && channelFuture.isSuccess()) {
                logger.info("返回成功!");
            }else{
                logger.info("socket出现错误!");
            }
        }

        return channelFuture.channel();
    }
}
