package com.ktds.zipzero.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface PaymentMapper {
    List<PaymentDTO> getListTest();

    @Select("select * from tbl_payment where m_id = #{payment.memberDTO.mid}")
    List<PaymentDTO> getUserPage(@Param("payment") PaymentDTO paymentDTO, @Param("pageDTO") PageDTO pageDTO);
    
    @Select("")
    List<PaymentDTO> getAdminPage(PaymentDTO paymentDTO, PageDTO pageDTO);


    
    @Select("")
    PaymentDTO getUserDetail(PaymentDTO paymentDTO);

    @Select("select * from tbl_payment where p_id = #{pid}")
    PaymentDTO getAdminDetail(PaymentDTO paymentDTO);

    /*
     * 만든사람 : 이은성(2022-08-10)
     * 최종수정 : 이은성(2022-08-10)
     * 기능 : 영수증 등록
     */
    void registPayment(PaymentDTO paymentDTO);
    
    @Select("")
    void deletePayment(PaymentDTO DTO, MemberDTO memberDTO);

    @Select("")
    void modifyPayment(PaymentDTO paymentDTO, MemberDTO memberDTO);

}
