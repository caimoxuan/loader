package com.cmx.loader.shiro.realm;


import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class FileUploadUserRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String userName = authenticationToken.getPrincipal().toString();
        String password = String.valueOf(((UsernamePasswordToken) authenticationToken).getPassword());

        return new SimpleAuthenticationInfo(userName, password, getName());
    }
}
