package com.ktds.zipzero.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/payment")
public class PaymentController {   

    @GetMapping("/userlist")
    public void paymentUserList(){
        log.info("userList");
    }

    @PostMapping("/regist")
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
    }
}
