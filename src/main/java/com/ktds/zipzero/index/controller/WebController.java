package com.ktds.zipzero.index.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.service.PaymentService;

import lombok.AllArgsConstructor;
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

    PaymentService paymentService;
    
    @GetMapping("/index")
    @PreAuthorize("isAuthenticated()")
    public String main(Model model, @AuthenticationPrincipal CustomUser customUser){
        Long mymid = customUser.getMember().getMid();
        PageDTO pageDTO = PageDTO.builder().page(1).size(5).total(5).build();
        pageDTO.setPaging();

        model.addAttribute("myPaymentList", paymentService.getMyPaymentsForMain(mymid));
        model.addAttribute("adminPaymentList", paymentService.getAdminPaymentsForMain(mymid));
        log.info("index");
        model.addAttribute("user", customUser);
        return "index";
    }

    @GetMapping("/nodirect")
    public String needAuth(@AuthenticationPrincipal CustomUser customUser){

        return "nodirect";
    }
}