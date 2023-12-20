package hiwth.cxn.gateway.core.socket.handler;

import hiwth.cxn.gateway.core.bind.IGenericReference;
import hiwth.cxn.gateway.core.socket.agreement.AgreementConstants;
import hiwth.cxn.gateway.core.socket.agreement.ResponseMessage;
import hiwth.cxn.gateway.core.mapping.HttpStatement;
import hiwth.cxn.gateway.core.session.Configuration;
import hiwth.cxn.gateway.core.session.defaults.DefaultGatewaySession;
import hiwth.cxn.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import hiwth.cxn.gateway.core.socket.BaseHandler;
import hiwth.cxn.gateway.core.socket.agreement.RequestParser;
import hiwth.cxn.gateway.core.socket.agreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ProtocolDataHandler extends BaseHandler<FullHttpRequest> {

    private Logger logger = LoggerFactory.getLogger(ProtocolDataHandler.class);
    private Configuration configuration;

    public ProtocolDataHandler(Configuration configuration) {
        this.configuration = configuration;
    }
    @Override
    protected void sessionHandle(ChannelHandlerContext context, FullHttpRequest request) {
        Channel channel = context.channel();

        HttpStatement httpStatement = channel.attr(AgreementConstants.HTTP_STATEMENT).get();

        String uri = httpStatement.getUri();
        try {
            // 开启session
            DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);
            DefaultGatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
            IGenericReference mapper = gatewaySession.getMapper(uri);

            Map<String, Object> params = new RequestParser(request).parse();

            Object res = mapper.$invoke(params);

            DefaultFullHttpResponse response = ResponseParser.parse(res);
            context.channel().writeAndFlush(response);
        } catch (Exception e) {
            DefaultFullHttpResponse response = ResponseParser.parse(new ResponseMessage(AgreementConstants.ResponseCode._500.getCode(), AgreementConstants.ResponseCode._500.getMsg(), null));
            context.channel().writeAndFlush(response);
        }
    }
}
