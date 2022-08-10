package com.ktds.zipzero.payment.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String paymentList(Model model, @RequestParam(value = "mid") long mid, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size){
        log.info("paymentList");
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        List<PaymentDTO> paymentList = paymentService.getPaymentList(mid, pageDTO.getSkip(), size);

        model.addAttribute("paymentList", paymentList);
        
        return "payment/userlist";
    }

    @PostMapping("/regist")
    public void paymentRegist(){
        log.info("paymentRegist");
    }
    
    @GetMapping("/adminlist")
    public String paymentAdminList(Model model, @RequestParam(value = "mid") long mid, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size){
        log.info("paymentList");
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        List<PaymentDTO> paymentList = paymentService.getPaymentList(mid, pageDTO.getSkip(), size);

        model.addAttribute("paymentList", paymentList);
        
        return "payment/adminlist";
    }
    /*
    @GetMapping("/adminmanage")
    public void paymentAdminManage(){
        log.info("adminManage");
    }
    */
    @GetMapping("/userdetail")
    public String paymentUserDetail(Model model, @RequestParam(value = "pid") long pid){
        log.info("UserDetail");
        
        model.addAttribute("payment", paymentService.getPaymentDetail(pid));

        return "payment/userdetail";
    }

    @GetMapping("/admindetail")
    public String paymentAdminDetail(Model model, @RequestParam(value = "pid") long pid){
        log.info("AdminDetail");

        model.addAttribute("payment", paymentService.getPaymentDetail(pid));

        return "payment/admindetail";
    }
    /*
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
