package com.ktds.zipzero.index.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ktds.zipzero.security.domain.CustomUser;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@AllArgsConstructor
@Log4j2
public class WebController {

    @GetMapping("/index")
    @PreAuthorize("isAuthenticated()")
    public String main(@AuthenticationPrincipal CustomUser customUser){
        log.info("index =================== " + customUser.getMember().getAuthid());
        return "index";
    }
}