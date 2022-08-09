package com.ktds.zipzero.payment;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.ktds.zipzero.all.PageDTO;
import com.ktds.zipzero.member.MemberDTO;

public class PaymentMapper {
    
    @Select("")
    List<PaymentDTO> getUserPage(PaymentDTO paymentDTO, PageDTO pageDTO){
        return null;
    }
    
    @Select("")
    List<PaymentDTO> getAdminPage(PaymentDTO paymentDTO, PageDTO pageDTO0){
        return null;
    }

    @Select("")
    PaymentDTO getUserDetail(PaymentDTO paymentDTO){
        return null;
    }

    @Select("")
    PaymentDTO getAdminDetail(PaymentDTO paymentDTO){
        return null;
    }

    @Select("")
    void registPayment(PaymentDTO paymentDTO){
        
    }

    @Select("")
    void deletePayment(PaymentDTO DTO, MemberDTO memberDTO){
        
    }

    @Select("")
    void modifyPayment(PaymentDTO paymentDTO, MemberDTO memberDTO){
        
    }

}
