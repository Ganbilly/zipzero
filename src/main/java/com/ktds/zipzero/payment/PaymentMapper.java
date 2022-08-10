package com.ktds.zipzero.payment;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.ktds.zipzero.all.PageDTO;
import com.ktds.zipzero.member.MemberDTO;

public interface PaymentMapper {
    
    List<PaymentDTO> getListTest();

    @Select("select * from tbl_payment")

    List<PaymentDTO> getUserPage(PaymentDTO paymentDTO, PageDTO pageDTO);
    
    @Select("")
    List<PaymentDTO> getAdminPage(PaymentDTO paymentDTO, PageDTO pageDTO0);

    @Select("")
    PaymentDTO getUserDetail(PaymentDTO paymentDTO);

    @Select("select * from tbl_payment where p_id = #{p_id}")
    PaymentDTO getAdminDetail(PaymentDTO paymentDTO);

    @Select("")
    void registPayment(PaymentDTO paymentDTO);

    @Select("")
    void deletePayment(PaymentDTO DTO, MemberDTO memberDTO);

    @Select("")
    void modifyPayment(PaymentDTO paymentDTO, MemberDTO memberDTO);

}
