package hiwth.cxn.gateway.core.session;

import hiwth.cxn.gateway.core.session.defaults.DefaultGatewaySessionFactory;

public class GatewaySessionFactoryBuilder {

    public DefaultGatewaySessionFactory build(Configuration configuration) {
        return new DefaultGatewaySessionFactory(configuration);
    }

}
