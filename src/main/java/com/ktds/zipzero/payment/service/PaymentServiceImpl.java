package com.ktds.zipzero.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/*
 * 만든 사람 : 정문경(2022-08-10)
 * 최종 수정 : 이은성(2022-08-11)
 * 기능 : 페이지 맵퍼
 */

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    @Setter(onMethod_ = @Autowired)
    private PaymentMapper paymentMapper;
    
    @Override
    public List<PaymentDTO> getPaymentList(long mid, int skip, int size) {
        List<PaymentDTO> paymentList = paymentMapper.getPage(mid, skip, size);
        return paymentList;
    }

    @Override
    public PaymentDTO getPaymentDetail(long pid) {
        PaymentDTO ppayment = paymentMapper.getPaymentById(pid);
        return ppayment;
    }

    /*
    * 만든 사람 : 이은성(2022-08-11)
    * 최종 수정 : 이은성(2022-08-11)
    * 기능 : 페이먼트 등록
    */
    @Override
    public void registPayment(PaymentDTO paymentDTO) {
        paymentMapper.registPayment(paymentDTO);
    }
}