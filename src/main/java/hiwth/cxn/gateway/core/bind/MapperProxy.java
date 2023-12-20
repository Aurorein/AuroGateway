package hiwth.cxn.gateway.core.bind;

import hiwth.cxn.gateway.core.mapping.HttpStatement;
import hiwth.cxn.gateway.core.session.IGatewaySession;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;

public class MapperProxy implements MethodInterceptor {
    public MapperProxy(IGatewaySession gatewaySession, String uri) {
        this.gatewaySession = gatewaySession;
        this.uri = uri;
    }

    private IGatewaySession gatewaySession;

    private String uri;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        HttpStatement httpStatement = gatewaySession.getConfiguration().getHttpStatement(uri);
        MapperMethod mapperMethod = new MapperMethod(gatewaySession, httpStatement);
        return mapperMethod.execute((Map<String, Object>) objects[0]);
    }
}
