package hiwth.cxn.gateway.core.session.defaults;

import hiwth.cxn.gateway.core.bind.IGenericReference;
import hiwth.cxn.gateway.core.session.Configuration;
import hiwth.cxn.gateway.core.session.IGatewaySession;
import hiwth.cxn.gateway.core.type.SimpleTypeRegistry;
import hiwth.cxn.gateway.core.executor.Executor;
import hiwth.cxn.gateway.core.executor.result.GatewayResult;
import hiwth.cxn.gateway.core.mapping.HttpStatement;

import java.util.Map;

public class DefaultGatewaySession implements IGatewaySession {

    Configuration configuration;

    Executor executor;

    String uri;

    public DefaultGatewaySession(Executor executor, Configuration configuration, String uri) {
        this.configuration = configuration;
        this.executor = executor;
        this.uri = uri;
    }

    @Override
    public IGenericReference getMapper(String uri) {
        return configuration.getReferenceRegistry().getMapper(uri, this);
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public GatewayResult get(Map<String, Object> params) {

        HttpStatement httpStatement = configuration.getHttpStatement(uri);
        String type = httpStatement.getParameterType();

        GatewayResult result = null;
        try {
            Object res = executor.execute(httpStatement.getMethodName(), new String[]{type}, (SimpleTypeRegistry.isSimpleType(type) ? params.values().toArray() : new Object[]{params}));
            result = GatewayResult.buildSuccess("执行成功!", res);
        }catch(Exception e) {
            return GatewayResult.buildFailure("执行出错!");
        }
        return result;
    }

    @Override
    public Object post(Map<String, Object> params) {
        return get(params);
    }
}
