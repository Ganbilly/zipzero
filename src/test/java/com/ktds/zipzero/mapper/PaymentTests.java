package com.ktds.zipzero.mapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.member.dto.AuthDTO;
import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class PaymentTests {
    @Autowired(required = false)
    PaymentMapper mapper;

    @Test
    public void getList(){
        mapper.getListTest().forEach(payment -> log.info(payment));
    }
    
    @Test
    public void testGetUserPage() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMid(1L);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPid(1L);
        paymentDTO.setMemberDTO(memberDTO);

        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(1);
        pageDTO.setSize(10);
        mapper.getUserPage(paymentDTO, pageDTO);
    }

    @Test
    public void testGetAdminDetail() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPid(2L);
        log.info(mapper.getAdminDetail(paymentDTO));
    }


    /*
     * 만든사람 : 이은성
     * 최종수정 : 이은성
     * 기능 : paymentRegist 테스트
    */
    @Test
    public void testRegistPayment(){
        PaymentDTO paymentDTO = new PaymentDTO();
        MemberDTO memberDTO = new MemberDTO();
        AuthDTO authDTO = new AuthDTO();
        authDTO.setAuthid(1L);
        authDTO.setAuthname("사원");
        memberDTO.setMid(16L);
        memberDTO.setMpw("wsx678");
        memberDTO.setMname("이은성");
        paymentDTO.setPname("test입니다");
        paymentDTO.setPtime(LocalDateTime.now());
        paymentDTO.setPregdate(LocalDateTime.now());
        paymentDTO.setPmoddate(LocalDateTime.now());
        paymentDTO.setPstorename("양갈비집");
        paymentDTO.setPtotalprice(100000L);
        paymentDTO.setPcardtype(true);
        paymentDTO.setPreceipt("test.png");


        //mapper.registPayment(paymentDTO);
    }
}
