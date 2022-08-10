package com.ktds.zipzero.payment.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/userlist")
    public String paymentList(Model model, PaymentDTO paymentDTO, PageDTO pageDTO){
        log.info("paymentList");
        List<PaymentDTO> paymentList = paymentService.getPaymentList(paymentDTO, pageDTO);

        model.addAttribute("paymentList", paymentList);
        
        return "payment/userlist";
    }

    /*@PostMapping("/regist")
    public void paymentRegist(){
        log.info("paymentRegist");
    }

    @GetMapping("/adminlist")
    public void paymentAdminList(){
        log.info("adminList");
    }

    @GetMapping("/adminmanage")
    public void paymentAdminManage(){
        log.info("adminManage");
    }

    @GetMapping("/userdetail")
    public void paymentUserDetail(){
        log.info("UserDetail");
    }

    @GetMapping("/admindetail")
    public void paymentAdminDetail(){
        log.info("AdminDetail");
    }

    @PostMapping("/modify")
    public void paymentModify(){
        log.info("Modify");
    }

    @PostMapping("/delete")
    public void paymentDelete(){
        log.info("Delete");
    }

    @GetMapping("/chart")
    public void paymentChart(){
        log.info("chart");
    }*/
}
