package hiwth.cxn.gateway.core.executor;

import hiwth.cxn.gateway.core.session.Configuration;
import hiwth.cxn.gateway.core.datasource.Connection;

public abstract class BaseExecutor implements Executor {

    public BaseExecutor(Connection connection, Configuration configuration) {
        this.connection = connection;
        this.configuration = configuration;
    }

    protected Configuration configuration;

    protected Connection connection;

    @Override
    public Object execute(String method, String[] paramTypes, Object[] args) {

        return doExc(method, paramTypes, args);

    }

    public abstract Object doExc(String method, String[] paramTypes, Object[] args);


}
