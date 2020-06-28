package com.money.controller;

import com.money.pojo.Admin;
import com.money.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("admin")
@SessionAttributes("userName")
public class CheckedLoginController {

    @Autowired
    private AdminLoginService adminLoginService;
    @RequestMapping("login")
    @ResponseBody
    public String login(String username, String password ,HttpSession session ){
       // System.out.println(username+password);
        Admin admin = new Admin();
        admin.setUserName(username);
        admin.setPassword(password);
        boolean flag = adminLoginService.checkAdmin(admin);
        if(flag==true){
            session.setAttribute("userName",username);
            return "success";
        }else{
            return "false";
        }

    }
    //退出登录
    @RequestMapping("exit")
    public String exit(){
        return "forward:/center/login";
    }

}
