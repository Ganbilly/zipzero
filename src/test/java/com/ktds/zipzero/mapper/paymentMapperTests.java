package com.ktds.zipzero.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class PaymentMapperTests {
    
    @Autowired(required = false)
    PaymentMapper mapper;
    @Test
    public void getListTest(){
        mapper.getPaymentList().forEach(payment -> log.info(payment));
    }
    
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
}