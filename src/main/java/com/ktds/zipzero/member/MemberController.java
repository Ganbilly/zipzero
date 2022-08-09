package com.ktds.zipzero.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/login")
@Log4j2
public class MemberController {
    @GetMapping("/login")
    public void login(){
        
    }

    @GetMapping("/login")
    public void logout(){

    }
}
