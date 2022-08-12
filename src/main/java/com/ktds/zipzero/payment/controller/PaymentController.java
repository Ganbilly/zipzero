package com.ktds.zipzero.payment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.all.dto.TimeDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.payment.dto.FilterDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.service.PaymentService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/payment")
@AllArgsConstructor
@Log4j2
public class PaymentController {
    private PaymentService paymentService;

 
 
    @GetMapping("/userlist")
    public String userPaymentList(Model model, @RequestParam(value = "mid") long mid, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size){
        log.info("PaymentList");
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        List<PaymentDTO> paymentList = paymentService.getPaymentList(mid, pageDTO.getSkip(), size);

        model.addAttribute("paymentList", paymentList);
        
        return "payment/userlist";
    }

    @PostMapping("/modify")
    public String paymentModify(Model model, @ModelAttribute("pid") long pid) {
        log.info("PaymentModify");
        model.addAttribute("payment", paymentService.getPaymentDetail(pid));

        return "payment/modify";
    }
    
    @PostMapping("/modifyresult")
    public String paymentModifyResult(Model model, @ModelAttribute("paymentDTO") PaymentDTO paymentDTO) {
        log.info("PaymentModifyResult");
        paymentDTO.setPmoddate(LocalDateTime.now());
        paymentDTO.setSid(3L);
        paymentDTO.setPcheck(1);
        paymentService.modifyPayment(paymentDTO);
        
        model.addAttribute("mid", paymentService.getPaymentDetail(paymentDTO.getPid()).getMid());

        return "redirect:userlist";
    }

    @PostMapping("/delete")
    public String paymentDelete(Model model, long pid) {
        log.info("PaymentDelete");
        PaymentDTO paymentDTO = paymentService.getPaymentDetail(pid);
        paymentDTO.setPcheck(0);
        paymentService.modifyPayment(paymentDTO);

        model.addAttribute("mid", paymentDTO.getMid());

        return "redirect:userlist";
    }

    @GetMapping("/adminlist")
    public String adminPaymentList(Model model, @RequestParam(value = "mid") long mid, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size){
        log.info("PaymentList");
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        FilterDTO filterDTO = FilterDTO.builder().build();
        List<FilterDTO> filterList = paymentService.getPaymentFilterList(filterDTO, pageDTO.getSkip(), size);

        model.addAttribute("filter", filterList);

        return "payment/adminlist";
    }

    @PostMapping("/adminlist")
    public String adminPaymentListFilter(Model model, @ModelAttribute("filterDTO") FilterDTO filterDTO, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "10") int size){
        log.info("PaymentListFilter : " + filterDTO);
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        List<FilterDTO> filterList = paymentService.getPaymentFilterList(filterDTO, pageDTO.getSkip(), size);

        model.addAttribute("filter", filterList);
        
        return "payment/adminlist";
    }

    @PostMapping("/adminsuccess")
    public String adminPaymentsuccess(Model model, long pid) {
        log.info("AdminManage");
        log.info("============  pid : " + pid);
        PaymentDTO paymentDTO = paymentService.getPaymentDetail(pid);
        paymentDTO.setSid(1L);
        paymentService.modifyPayment(paymentDTO);

        model.addAttribute("mid", paymentDTO.getMid());
        
        return "redirect:adminlist";
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
    * 만든 사람 : 김예림(2022-08-10)
    * 최종 수정 : 김예림(2022-08-12)
    * 기능 : 본인 소속의 모든 직원 영수증 내역 조회
    */
    @GetMapping("/adminmanage")
    public String adminlist(Model model, @RequestParam(value = "mid") long mid){
        log.info("adminManage");
        
        model.addAttribute("adminpaymentList", paymentService.getAuthList(mid));

        return "payment/adminmanage";
    }

    /*
     * 만든 사람 : 정문경 (2022-08-12)
     * 최종 수정 : 정문경 (2022-08-12)
     * 기능 : detail에서 작성한 모달의 결과를 저장하고 adminmanage 페이지 반환
     */
    @PostMapping("/adminmanage")
    public String adminManageReject(Model model, @ModelAttribute("commentDTO") CommentDTO commentDTO){
        log.info("adminManageReject");
        commentDTO.setCregdate(LocalDateTime.now());
        commentDTO.setCmoddate(LocalDateTime.now());
        commentDTO.setCcheck(1);
        commentDTO.setMid(paymentService.getMidByPid(commentDTO.getPid()));

        paymentService.registComment(commentDTO);
        
        model.addAttribute("adminpaymentList", paymentService.getAuthList(commentDTO.getMid()));

        return "payment/adminmanage";
    }
    
    @GetMapping("/userdetail")
    public String userDetail(Model model, @RequestParam(value = "pid") long pid){
        log.info("UserDetail");
        
        model.addAttribute("payment", paymentService.getPaymentDetail(pid));

        return "payment/userdetail";
    }
    
    @GetMapping("/admindetail")
    public String adminDetail(Model model, @RequestParam(value = "pid") long pid){
        log.info("AdminrDetail");
        
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
