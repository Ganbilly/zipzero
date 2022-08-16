package com.ktds.zipzero.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.payment.dto.FilterDTO;

import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface PaymentMapper {
    /* 
     * 만든사람 : 정문경(2022-08-10)
     * 최종수정 : 정문경(2022-08-10)
     * 기능 : 영수증 상세 내역
    */
    PaymentDTO getDetail(PaymentDTO paymentDTO);

    /*
     * 만든사람 : 정문경(2022-08-10)
     * 최종수정 : 정문경(2022-08-10)
     * 기능 : 영수증 목록 가져오기
     */
    public List<PaymentDTO> getUserPage(@Param("mid") long mid, @Param("skip") int skip, @Param("size") int size);
    public List<FilterDTO> getAdminPage(@Param("filter") FilterDTO filterDTO, @Param("skip") int skip, @Param("size") int size);


    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : 영수증 등록/수정/삭제
     */

    void registPayment(PaymentDTO paymentDTO);
    void modifyPayment(PaymentDTO paymentDTO);
    void deletePayment(PaymentDTO paymentDTO);

    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : pid로 영수증 목록 가져오기
     */
    public List<PaymentDTO> getPageByPid(@Param("pid") long pid, @Param("skip") int skip, @Param("size") int size);

    /*
     * 만든사람 : 정문경(2022-08-10)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : pid로 영수증 가져오기
     */
    public PaymentDTO getPaymentByPid(Long pid);

    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : mid로 이름 가져오기
     */
    String getMnameByMid(@Param("mid") long mid);

    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : pid로 mid 가져오기
     */
    long getMidByPid(@Param("pid") long pid);

    /*
    * 만든 사람 : 김예림(2022-08-10)
    * 최종 수정 : 김예림(2022-08-12)
    * 기능 : 본인 소속의 모든 영수증 내역 조회
    */
    public List<PaymentDTO> getMidListByAuth(@Param("mid") Long mid);
   
    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 정문경(2022-08-12)
     * 기능 : 댓글 등록
     */
    public void registComment(@Param("comment") CommentDTO commentDTO);
}

   

