package com.cmx.worktest.shirocontroller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginHtml(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, HttpServletRequest request){
        String shiroException = (String)request.getAttribute("shiroLoginFailure");
        if(null != shiroException){
            System.out.println(shiroException);
            if(shiroException.equals(AuthenticationException.class.getName())){}
            return "login";
        }

        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            model.addAttribute("message", "请勿重复登陆");
            System.out.println("重复登入");
        }else{
            throw new AuthenticationException("用户状态错误");
        }

        return "pages/index";
    }

    @RequestMapping("/pages/index")
    public String loginSuccess(){
        return "pages/index";
    }


}
