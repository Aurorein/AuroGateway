package hiwth.cxn.gateway.core.bind;

import hiwth.cxn.gateway.core.mapping.HttpStatement;
import hiwth.cxn.gateway.core.session.Configuration;
import hiwth.cxn.gateway.core.session.defaults.DefaultGatewaySession;


import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {
    private Configuration configuration;

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    private Map<String, MapperProxyFactory> knownMapper = new HashMap<>();

    public IGenericReference getMapper(String uri, DefaultGatewaySession gatewaySession) {

        MapperProxyFactory referenceProxyFactory = knownMapper.get(uri);
        if(referenceProxyFactory == null) {
            throw new RuntimeException();
        }
        return referenceProxyFactory.getInstance(gatewaySession);
    }

    public void addMapper(HttpStatement statement) {

        String uri = statement.getUri();
        knownMapper.put(uri, new MapperProxyFactory(uri));

        configuration.addHttpStatement(statement);
    }



}
