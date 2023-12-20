package hiwth.cxn.gateway.core.auth;

import hiwth.cxn.gateway.core.auth.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayAuthorizingRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(GatewayAuthorizingRealm.class);

    @Override
    public Class<?> getAuthenticationTokenClass() {
        return GatewayAuthorizingToken.class;
    }
    // 认证处理
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Claims claims = null;
        try {
            claims = JwtUtil.decode(((GatewayAuthorizingToken) token).getJwt());
        } catch(Exception e) {
            logger.error("jwt解析错误!");
        }

        return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), this.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
