package hiwth.cxn.gateway.core.socket.handler;

import hiwth.cxn.gateway.core.socket.agreement.AgreementConstants;
import hiwth.cxn.gateway.core.socket.agreement.RequestParser;
import hiwth.cxn.gateway.core.socket.agreement.ResponseMessage;
import hiwth.cxn.gateway.core.mapping.HttpStatement;
import hiwth.cxn.gateway.core.session.Configuration;
import hiwth.cxn.gateway.core.socket.BaseHandler;
import hiwth.cxn.gateway.core.socket.agreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayServerHandler extends BaseHandler<FullHttpRequest> {
    private Logger logger = LoggerFactory.getLogger(GatewayServerHandler.class);
    private Configuration configuration;

    public GatewayServerHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void sessionHandle(ChannelHandlerContext context, FullHttpRequest request) {
        logger.info("网管收到来自{}的请求", request.getUri());
        try {
            RequestParser requestParser = new RequestParser(request);
            String uri = requestParser.getUri();
            HttpStatement httpStatement = configuration.getHttpStatement(uri);

            Channel channel = context.channel();
            channel.attr(AgreementConstants.HTTP_STATEMENT).set(httpStatement);

            // 放行
            request.retain();
            context.fireChannelRead(request);
        }catch (Exception e) {
            DefaultFullHttpResponse response = ResponseParser.parse(new ResponseMessage(AgreementConstants.ResponseCode._404.getCode(), AgreementConstants.ResponseCode._404.getMsg(), null));
            context.channel().writeAndFlush(response);

        }


    }
}
