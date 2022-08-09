package com.ktds.zipzero.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member")
@Log4j2
public class MemberController {
    @GetMapping("/login")
    public void login(){

    }

    @PostMapping("/login")
    public void logout(){

    }
}
