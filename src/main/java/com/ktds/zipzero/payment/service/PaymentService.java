package com.ktds.zipzero.payment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentService {
    private final PaymentMapper paymentMapper;
    
    public List<PaymentDTO> getPaymentList(PaymentDTO paymentDTO, PageDTO pageDTO) {
        paymentDTO.setMid(2L);
        List<PaymentDTO> paymentList = paymentMapper.getPage(paymentDTO, pageDTO);
        return paymentList;
    }
}
