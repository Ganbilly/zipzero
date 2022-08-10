package com.ktds.zipzero.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/payment")
@Log4j2
public class PaymentController {

    @GetMapping("/userlist")
    public void paymentList(){
        
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
