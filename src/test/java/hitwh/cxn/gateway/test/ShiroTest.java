package hitwh.cxn.gateway.test;

import hiwth.cxn.gateway.core.auth.AuthService;
import hiwth.cxn.gateway.core.auth.jwt.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ShiroTest {

    private Logger logger = LoggerFactory.getLogger(ShiroTest.class);
    @Test
    public void testShiro() {
        IniSecurityManagerFactory securityManagerFactory = new IniSecurityManagerFactory("classpath:test-shiro.ini");

        SecurityManager securityManager = securityManagerFactory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        if(!subject.isAuthenticated()){
            System.out.println("验证失败!");
        }

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("cxn", "123456");
        subject.login(usernamePasswordToken);

        Object principal1 = usernamePasswordToken.getPrincipal();
        Object credentials = usernamePasswordToken.getCredentials();
        System.out.println(principal1.toString());
        System.out.println(credentials.toString());

        if(subject.isAuthenticated()){
            System.out.println("验证成功!");
        }
    }

    @Test
    public void test_auth() {
        AuthService authService = new AuthService();
        boolean isSuccess = authService.validate("DPij8iUY", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjeG4iLCJuYW1lIjoiY3huIiwiZXhwIjoxNzAzNjgxMjAyLCJpYXQiOjE3MDMwNzY0MDJ9.Vhjsg_wAdmLqx89fD982GyXmxj6Il173T3ivP44BQ4Y");
        System.out.println((isSuccess ? "认证成功!" : "认证失败!"));

    }

    @Test
    public void test_jwt() {

        Map<String, Object> claims = new HashMap<>();
        claims.put("name","cxn");
        String jwt = JwtUtil.encode(claims);
        System.out.println(jwt);
    }
}
