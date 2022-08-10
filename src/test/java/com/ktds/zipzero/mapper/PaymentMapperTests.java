package com.ktds.zipzero.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktds.zipzero.all.PageDTO;
import com.ktds.zipzero.payment.PaymentDTO;
import com.ktds.zipzero.payment.PaymentMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class PaymentMapperTests {
    @Autowired(required = false)
    PaymentMapper mapper;

    @Test
    public void testGetUserPage() {
        PaymentDTO paymentDTO = new PaymentDTO();
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(1);
        pageDTO.setSize(10);
        log.info(mapper.getUserPage(paymentDTO, pageDTO));
    }

    @Test
    public void testGetAdminDetail() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setP_id(2L);
        log.info(mapper.getAdminDetail(paymentDTO));
    }
}
