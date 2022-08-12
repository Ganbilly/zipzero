package com.ktds.zipzero.payment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.all.dto.TimeDTO;
import com.ktds.zipzero.comment.service.CommentService;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;
import com.ktds.zipzero.payment.service.PaymentService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/payment")
@AllArgsConstructor
@Log4j2
public class PaymentController {

    private final PaymentService paymentService;
    private final CommentService commentService;

    @GetMapping("/userlist")
    public String paymentList(Model model, @RequestParam(value = "mid") long mid, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size){
        log.info("paymentList");
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        List<PaymentDTO> paymentList = paymentService.getPaymentList(mid, pageDTO.getSkip(), size);

        model.addAttribute("paymentList", paymentList);
        
        return "payment/userlist";
    }
    
    /*
     * 만든사람 : 이은성(2022-08-10)
     * 최종수정 : 이은성(2022-08-10)
     * 기능 : /payment/regist로 접속하면 regist.html페이지 연결 
     */
    @GetMapping("/regist")
    public String getPaymentRegist(){
        log.info("paymentRegist");
        return "payment/regist";
    }
    
    /*
    * 만든사람 : 이은성(2022-08-10)
    * 최종수정 : 이은성(2022-08-10)
    * 기능 : /payment/regist 페이지에서 영수증 등록
    */
    @PostMapping("/regist")
    public void postPaymentRegist(PaymentDTO paymentDTO, TimeDTO timeDTO){

        /* 
         * service로옮길부분
         */
        // log.info(paymentDTO.toString());
        // LocalDateTime t = LocalDateTime.parse(timeDTO.getTime());
        // paymentDTO.setPtime(t);
        // paymentDTO.setPregdate(LocalDateTime.now());
        // paymentDTO.setPmoddate(LocalDateTime.now());
        // paymentDTO.setPreceipt("test.jpg");
        // paymentDTO.setPcheck(0);
        // paymentDTO.setPcurstate(1L);
        // paymentDTO.setPfinstate(1L);
        // paymentDTO.setSid(1L);
        // paymentDTO.setMid(1L);
        // paymentDTO.setPtypecode(0L);

        // paymentMapper.registPayment(paymentDTO);
    }
    


    /*
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

        model.addAttribute("comments", commentService.getCommentList(pid, 0, 5));

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
