package com.cxp.springboot2shiro.controller;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-19 22:18
 */
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class JspController {

    @RequestMapping("/jsp")
    public String toJps(Model model) {
        model.addAttribute("welcome", "不建议使用jsp");
        System.out.println("toJps");
        return "welcome";
    }

    @RequestMapping(value = {"/","/login"})
    public String login(Model model) {
        System.out.println("login");
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(Model model,String username,String password) {
        System.out.println("doLogin: username = "+username +", password = "+password);

        String error = null;
        if (username != null && password != null) {
            //初始化
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                //登录，即身份校验，由通过Spring注入的UserRealm会自动校验输入的用户名和密码在数据库中是否有对应的值
                subject.login(token);
                return "200";
            }catch (Exception e){
                e.printStackTrace();
                error = "未知错误，错误信息：" + e.getMessage();
            }
        } else {
            error = "请输入用户名和密码";
        }
        return error;
    }
}
