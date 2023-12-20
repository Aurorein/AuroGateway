package hiwth.cxn.gateway.core.socket.handler;

import hiwth.cxn.gateway.core.mapping.HttpStatement;
import hiwth.cxn.gateway.core.session.Configuration;
import hiwth.cxn.gateway.core.socket.BaseHandler;
import hiwth.cxn.gateway.core.socket.agreement.AgreementConstants;
import hiwth.cxn.gateway.core.socket.agreement.ResponseMessage;
import hiwth.cxn.gateway.core.socket.agreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;


public class AuthorizationHandler extends BaseHandler<FullHttpRequest> {
    private Configuration configuration;

    public AuthorizationHandler(Configuration configuration) {
        this.configuration = configuration;
    }
    @Override
    protected void sessionHandle(ChannelHandlerContext context, FullHttpRequest request) {
        Channel channel = context.channel();

        HttpStatement httpStatement = channel.attr(AgreementConstants.HTTP_STATEMENT).get();
        if(httpStatement.isAuth()) {
            try{
                String uId = request.headers().get("uId");
                String token = request.headers().get("token");

                if(token == null || "".equals(token)){
                    DefaultFullHttpResponse response = ResponseParser.parse(new ResponseMessage(AgreementConstants.ResponseCode._400.getCode(), "不含token", null));
                    context.channel().writeAndFlush(response);
                }

                boolean authed = configuration.auth(uId, token);
                if(authed) {
                    request.retain();
                    context.fireChannelRead(request);
                } else{
                    // 验证未通过
                    DefaultFullHttpResponse response = ResponseParser.parse(new ResponseMessage(AgreementConstants.ResponseCode._403.getCode(), "token验证未通过", null));
                    context.channel().writeAndFlush(response);
                }

            }catch (Exception e) {
                DefaultFullHttpResponse response = ResponseParser.parse(new ResponseMessage(AgreementConstants.ResponseCode._403.getCode(), "token验证未通过", null));
                context.channel().writeAndFlush(response);
            }
        }else{
            // 不需要验证
            request.retain();
            context.fireChannelRead(request);
        }

    }
}
