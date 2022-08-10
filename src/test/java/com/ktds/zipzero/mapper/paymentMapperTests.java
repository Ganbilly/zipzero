package com.ktds.zipzero.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktds.zipzero.payment.PaymentMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class paymentMapperTests {
    
    @Autowired(required = false)
    PaymentMapper mapper;

    @Test
    public void getList(){
        mapper.getListTest().forEach(payment -> log.info(payment));
    }
}
