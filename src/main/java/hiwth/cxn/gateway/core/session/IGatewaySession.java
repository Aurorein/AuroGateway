package hiwth.cxn.gateway.core.session;

import hiwth.cxn.gateway.core.bind.IGenericReference;

import java.util.Map;

public interface IGatewaySession {

    IGenericReference getMapper(String uri);

    Configuration getConfiguration();

    Object get(Map<String, Object> params);

    Object post(Map<String, Object> params);
}
