package com.ktds.zipzero.index.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ktds.zipzero.security.domain.CustomUser;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@AllArgsConstructor
@Log4j2
public class WebController {
    @GetMapping("/")
    public String main() {
        return "/member/login";
    }

    @GetMapping("/index")
    @PreAuthorize("isAuthenticated()")
    public String main(Model model, @AuthenticationPrincipal CustomUser customUser){
        log.info("index");
        model.addAttribute("user", customUser);

        return "index";
    }
}