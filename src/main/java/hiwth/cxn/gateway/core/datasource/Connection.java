package hiwth.cxn.gateway.core.datasource;

public interface Connection {

    Object execute(String method, String[] paramTypes, Object[] params);
}
