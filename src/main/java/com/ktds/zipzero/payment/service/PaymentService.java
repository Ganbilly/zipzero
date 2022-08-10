package com.ktds.zipzero.payment.service;

import java.util.List;

import com.ktds.zipzero.payment.dto.PaymentDTO;

/*
 * 만든 사람 : 정문경(2022-08-10)
 * 최종 수정 : 정문경(2022-08-10)
 * 기능 : 페이지 맵퍼
 */
public interface PaymentService {

    public List<PaymentDTO> getPaymentList(long mid, int skip, int size);

    public PaymentDTO getPaymentDetail(long pid);
}
