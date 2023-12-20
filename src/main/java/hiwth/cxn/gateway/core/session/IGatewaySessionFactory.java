package hiwth.cxn.gateway.core.session;

import hiwth.cxn.gateway.core.session.defaults.DefaultGatewaySession;

public interface IGatewaySessionFactory {

    DefaultGatewaySession openSession(String uri);

}
