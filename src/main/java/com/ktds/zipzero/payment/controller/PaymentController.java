// package com.ktds.zipzero.payment.controller;

// import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.ktds.zipzero.payment.dto.PaymentDTO;
// import com.ktds.zipzero.payment.service.PaymentService;

// import lombok.extern.log4j.Log4j2;

// @Controller
// @Log4j2
// @RequestMapping("/payment")
// public class PaymentController {  

//     private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    
//     private PaymentService paymentService;

//     public PaymentController(PaymentService paymentService) {
//         this.paymentService = paymentService;
//     }

//     @GetMapping("/userlist")
//     public String paymentUserList(Model model){

//         // log.info("userList");
//         List<PaymentDTO> paymentList = paymentService.getPaymentList();

//         model.addAttribute("title", "영수증목록조회");
//         model.addAttribute("영수증목록조회", paymentList);

//         return "payment/userlist";
//     }

//     @PostMapping("/regist")
//     public void paymentRegist(){
//         log.info("paymentRegist");
//     }

//     @GetMapping("/adminlist")
//     public void paymentAdminList(){
//         log.info("adminList");
//     }

//     @GetMapping("/adminmanage")
//     public void paymentAdminManage(){
//         log.info("adminManage");
//     }

//     @GetMapping("/userdetail")
//     public void paymentUserDetail(){
//         log.info("UserDetail");
//     }

//     @GetMapping("/admindetail")
//     public void paymentAdminDetail(){
//         log.info("AdminDetail");
//     }

//     @PostMapping("/modify")
//     public void paymentModify(){
//         log.info("Modify");
//     }

//     @PostMapping("/delete")
//     public void paymentDelete(){
//         log.info("Delete");
//     }

//     @GetMapping("/chart")
//     public void paymentChart(){
//         log.info("chart");
//     }
// }
