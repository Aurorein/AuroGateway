package hiwth.cxn.gateway.core.executor;

import hiwth.cxn.gateway.core.datasource.Connection;
import hiwth.cxn.gateway.core.session.Configuration;

public class GatewayExecutor extends BaseExecutor{

    public GatewayExecutor(Connection connection, Configuration configuration) {
        super(connection, configuration);
    }
    @Override
    public Object doExc(String method, String[] paramTypes, Object[] args) {
        return connection.execute(method, paramTypes, args);
    }
}
