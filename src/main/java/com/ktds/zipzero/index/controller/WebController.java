package com.ktds.zipzero.index.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.service.PaymentService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class WebController {

    PaymentService paymentService;
    
    @GetMapping("/index")
    public String main(Model model){
        Long mymid = 1L;
        PageDTO pageDTO = PageDTO.builder().page(1).size(5).total(5).build();
        pageDTO.setPaging();

        model.addAttribute("myPaymentList", paymentService.getMyPaymentsForMain(mymid));
        model.addAttribute("adminPaymentList", paymentService.getAdminPaymentsForMain(mymid));

        return "index";
    }
}