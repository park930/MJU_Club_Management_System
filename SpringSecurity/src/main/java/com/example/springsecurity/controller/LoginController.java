package com.example.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")   //login페이지로 접근할 시,
    public String loginP(){
        
        return "login"; //login 요청이 오면  return 값인 login.mustache로 전송해주는 역할
    }
}



