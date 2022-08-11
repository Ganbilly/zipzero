package com.ktds.zipzero.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface PaymentMapper {

    /* 
     * 만든사람 : 정문경(2022-08-10)
     * 최종수정 : 정문경(2022-08-10)
     * 기능 : 영수증 상세 내역
    */
    
    PaymentDTO getUserDetail(PaymentDTO paymentDTO);

    PaymentDTO getAdminDetail(PaymentDTO paymentDTO);

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

    // public List<CommentDTO> getComment(@Param("cid") long cid, @Param("skip") int skip, @Param("size") int size);    

    void registPayment(PaymentDTO paymentDTO);

    void deletePayment(PaymentDTO paymentDTO);

    void modifyPayment(PaymentDTO paymentDTO);

    /*
     * 만든사람 : 정문경(2022-08-10)
     * 최종수정 : 정문경(2022-08-10)
     * 기능 : 영수증 가져오기
     */
    public PaymentDTO getPaymentById(Long pid);
}
