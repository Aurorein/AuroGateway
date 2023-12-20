package hiwth.cxn.gateway.core.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthService implements IAuth{
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    private Subject subject;

    public AuthService() {
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = securityManagerFactory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
    }



    @Override
    public boolean validate(String id, String jwt) {

        try {
            subject.login(new GatewayAuthorizingToken(id, jwt));
        } catch (Exception e) {
            logger.error("验证失败!");
        }

        return subject.isAuthenticated();
    }
}
