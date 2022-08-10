package com.ktds.zipzero.mapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.dto.PaytypeDTO;
import com.ktds.zipzero.payment.dto.StateDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class PaymentTests {
    @Autowired(required = false)
    PaymentMapper mapper;
    
    /*
     * 만든 사람 : 정문경
     * 최종 수정 : 정문경
     * 기능 : mid와 pid가 일치하는 payment 데이터들을 (page - 1) * size부터 10개 가져옴
     */
    @Test
    public void testGetPage() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMid(1L);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPid(1L);
        paymentDTO.setMemberDTO(memberDTO);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(1);
        pageDTO.setSize(10);
        mapper.getPage(paymentDTO, pageDTO);
    }

    /*
     * 만든 사람 : 정문경
     * 최종 수정 : 정문경
     * 기능 : pid가 맞는 영수증 정보 가져옴
     */
    @Test
    public void testGetDetail() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPid(3L);
        log.info(mapper.getDetail(paymentDTO));
    }

    /*
     * 만든 사람 : 정문경
     * 최종 수정 : 정문경
     * 기능 : pid가 맞는 영수증 데이터의 p_check를 0으로 변경
     */
    @Test
    public void testDeletePayment() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPid(2L);
        mapper.deletePayment(paymentDTO);
    }

    /*
     * 만든 사람 : 정문경
     * 최종 수정 : 정문경
     * 기능 : db의 영수증 데이터를 paymentDTO로 변경
     */
    @Test
    public void testModifyPayment() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPname("p_name"); // 결제내역
        paymentDTO.setPtime(LocalDateTime.now().plusDays(2)); // 결제시각
        paymentDTO.setPmoddate(LocalDateTime.now()); // 결제건수정시간
        paymentDTO.setPstorename("p_storename"); // 결제 상호명
        paymentDTO.setPtotalprice(12345L); // 결제총액
        paymentDTO.setPcardtype(1); // 결제카드유형
        paymentDTO.setPreceipt("p_receipt"); // 영수증
        paymentDTO.setPcurstate(1L); // 삭제여부
        paymentDTO.setPfinstate(3L); // 최종결제상태

        StateDTO stateDTO = new StateDTO();
        stateDTO.setSid(3L);
        paymentDTO.setStateDTO(stateDTO); // 상태아이디

        PaytypeDTO paytypeDTO = new PaytypeDTO();
        paytypeDTO.setPtypecode(2L);
        paymentDTO.setPaytypeDTO(paytypeDTO); // 회원아이디
        
        paymentDTO.setPid(2L);
        mapper.modifyPayment(paymentDTO);
    }
}
