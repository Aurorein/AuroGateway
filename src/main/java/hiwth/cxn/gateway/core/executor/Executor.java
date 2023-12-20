package hiwth.cxn.gateway.core.executor;

public interface Executor {

    Object execute(String method, String[] paramTypes, Object[] args);
}
