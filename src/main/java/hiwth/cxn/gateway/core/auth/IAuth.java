package hiwth.cxn.gateway.core.auth;

public interface IAuth {

    boolean validate(String id, String jwt);
}
