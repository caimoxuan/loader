package com.cmx.worktest.shiro.matcher;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.beans.factory.annotation.Value;

import java.security.Security;

public class FileUploadCredentialMatcher implements CredentialsMatcher {


    @Value("${shiro.upload.username}")
    private String username;

    @Value("${shiro.upload.password}")
    private String password;


    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {

        String userName = authenticationInfo.getPrincipals().toString();
        String passWord = authenticationInfo.getCredentials().toString();

        
        if(username.equals(userName) || password.equals(passWord)){
            System.out.println("密码正确");
            return true;
        }
        System.out.println("用户名密码不能匹配");
        return false;
    }
}
