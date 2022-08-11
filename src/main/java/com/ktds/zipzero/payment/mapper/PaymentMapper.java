package com.ktds.zipzero.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface PaymentMapper {

    /* 
     * 만든사람 : 정문경(2022-08-10)
     * 최종수정 : 정문경(2022-08-10)
     * 기능 : 영수증 상세 내역
    */
    
    PaymentDTO getUserDetail(PaymentDTO paymentDTO);
    
        /* 
     * 만든사람 : 김예림(2022-08-11)
     * 최종수정 : 
     * 기능 : 직급별 조회
    */
    //PaymentDTO getAdminDetail(PaymentDTO paymentDTO);

    public PaymentDTO getAdminDetail(Long mid);

    /*
     * 만든사람 : 이은성(2022-08-10)
     * 최종수정 : 이은성(2022-08-10)
     * 기능 : 영수증 등록
     */
    PaymentDTO getDetail(PaymentDTO paymentDTO);

    /*
     * 만든사람 : 정문경(2022-08-10)
     * 최종수정 : 정문경(2022-08-10)
     * 기능 : 페이지 가져오기, 영수증 삭제, 영수증 수정
     */
    public List<PaymentDTO> getPage(@Param("mid") long mid, @Param("skip") int skip, @Param("size") int size);

    void registPayment(PaymentDTO paymentDTO);

    void deletePayment(PaymentDTO paymentDTO);

    void modifyPayment(PaymentDTO paymentDTO);

    /*
     * 만든사람 : 정문경(2022-08-10)
     * 최종수정 : 정문경(2022-08-10)
     * 기능 : 영수증 가져오기
     */
    public PaymentDTO getPaymentById(Long pid);

    public List<PaymentDTO> getMidListByAuth(Long mid);
   
}
