package com.ktds.zipzero.payment.service;

import java.util.List;

import com.ktds.zipzero.payment.dto.PaymentDTO;

/*
 * 만든 사람 : 정문경(2022-08-10)
 * 최종 수정 : 이은성(2022-08-11)
 * 기능 : 페이먼트 서비스
 */
public interface PaymentService {

    public List<PaymentDTO> getPaymentList(long mid, int skip, int size);

    public PaymentDTO getPaymentDetail(long pid);

    /*
    * 만든 사람 : 이은성(2022-08-11)
    * 최종 수정 : 이은성(2022-08-11)
    * 기능 : 페이먼트 등록
    */
    public void registPayment(PaymentDTO paymentDTO);


    public List<PaymentDTO> getAuthList(long mid);


  

}
