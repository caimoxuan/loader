package com.cmx.loader.config;


import com.cmx.loader.shiro.matcher.FileUploadCredentialMatcher;
import com.cmx.loader.shiro.realm.FileUploadUserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public EhCacheManager getEhcacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }


    @Bean
    public FileUploadCredentialMatcher getFileUploadCredentialMatcher(){
        FileUploadCredentialMatcher fileUploadCredentialMatcher = new FileUploadCredentialMatcher();
        return fileUploadCredentialMatcher;
    }

    @Bean
    public FileUploadUserRealm getFileUploadUserRealm(){
        FileUploadUserRealm fileUploadUserRealm = new FileUploadUserRealm();
        fileUploadUserRealm.setCacheManager(getEhcacheManager());
        fileUploadUserRealm.setCredentialsMatcher(getFileUploadCredentialMatcher());
        return fileUploadUserRealm;
    }


    @Bean
    public DefaultWebSecurityManager getDefaultSecurityManager(EhCacheManager ehCacheManager){
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setCacheManager(ehCacheManager);

        Collection<Realm> realms = new ArrayList<>();
        realms.add(getFileUploadUserRealm());
        defaultSecurityManager.setRealms(realms);

        return defaultSecurityManager;
    }



    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager defaultSecurityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(defaultSecurityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/operatorFile/home");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //静态资源放行
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/style/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        //登陆拦截 使用默认表单验证filter 暂时不支持rememberMe
        filterChainDefinitionMap.put("/login", "authc");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        return shiroFilterFactoryBean;
    }

    /**
     * 生命周期配置
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启shiro的注解需要的两个类
     */
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setExposeProxy(true);
        return defaultAdvisorAutoProxyCreator;
    }


    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultSecurityManager defaultSecurityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
