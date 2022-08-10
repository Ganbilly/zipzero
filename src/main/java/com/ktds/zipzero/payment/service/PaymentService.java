package com.ktds.zipzero.payment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
/*
 * 만든 사람 : 정문경(2022-08-10)
 * 최종 수정 : 정문경(2022-08-10)
 * 기능 : 페이지 맵퍼
 */
public class PaymentService {
    private final PaymentMapper paymentMapper;
    
    public List<PaymentDTO> getPaymentList(long mid, int skip, int size) {
        List<PaymentDTO> paymentList = paymentMapper.getPage(mid, skip, size);
        return paymentList;
    }

    public PaymentDTO getPaymentDetail(long pid) {
        PaymentDTO ppayment = paymentMapper.getPaymentById(pid); // 테스트용
        return ppayment;
    }
}
