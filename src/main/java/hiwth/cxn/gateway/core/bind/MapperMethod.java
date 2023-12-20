package hiwth.cxn.gateway.core.bind;

import hiwth.cxn.gateway.core.session.IGatewaySession;
import hiwth.cxn.gateway.core.mapping.HttpStatement;

import java.util.Map;

public class MapperMethod {

    private IGatewaySession gatewaySession;

    private HttpStatement httpStatement;

    public MapperMethod(IGatewaySession gatewaySession, HttpStatement httpStatement) {
        this.httpStatement = httpStatement;
        this.gatewaySession = gatewaySession;
    }

    public Object execute(Map<String, Object> args) {
        Object res = null;
        switch(httpStatement.getHttpType()) {
            case GET:{
                res = gatewaySession.get(args);
                System.out.println("调用Get");
                break;
            }
            case POST:{
                res = gatewaySession.post(args);
                System.out.println("调用Post");
                break;
            }
            case PUT:{
                System.out.println("put");
                break;
            }
            case DELETE:{
                break;
            }
        }

        return res;
    }

}
