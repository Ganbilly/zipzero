package com.ktds.zipzero.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface PaymentMapper {
    
    PaymentDTO getUserDetail(PaymentDTO paymentDTO);

    PaymentDTO getAdminDetail(PaymentDTO paymentDTO);

    /*
     * 만든사람 : 이은성(2022-08-10)
     * 최종수정 : 이은성(2022-08-10)
     * 기능 : 영수증 등록
     */
    PaymentDTO getDetail(PaymentDTO paymentDTO);

    public List<PaymentDTO> getPage(@Param("payment") PaymentDTO paymentDTO, @Param("page") PageDTO pageDTO);

    void registPayment(PaymentDTO paymentDTO);

    void deletePayment(PaymentDTO paymentDTO);

    void modifyPayment(PaymentDTO paymentDTO);

}
