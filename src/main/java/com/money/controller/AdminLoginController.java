package com.money.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("center")
public class AdminLoginController {

    @RequestMapping("login")
    public String login() {

        return "behind/login";
    }


}


