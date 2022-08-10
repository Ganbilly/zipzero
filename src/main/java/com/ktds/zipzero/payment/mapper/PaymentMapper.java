package com.ktds.zipzero.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface PaymentMapper {

    List<PaymentDTO> getPage(@Param("payment") PaymentDTO paymentDTO, @Param("pageDTO") PageDTO pageDTO);

    PaymentDTO getDetail(PaymentDTO paymentDTO);

    @Select("")
    void registPayment(PaymentDTO paymentDTO);

    void deletePayment(@Param("payment") PaymentDTO paymentDTO, @Param("member") MemberDTO memberDTO);

    @Select("")
    void modifyPayment(PaymentDTO paymentDTO, MemberDTO memberDTO);

}
