package com.ktds.zipzero.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface PaymentMapper {

    public List<PaymentDTO> getPage(@Param("payment") PaymentDTO paymentDTO, @Param("page") PageDTO pageDTO);

    PaymentDTO getDetail(PaymentDTO paymentDTO);

    void registPayment(PaymentDTO paymentDTO);

    void deletePayment(PaymentDTO paymentDTO);

    void modifyPayment(PaymentDTO paymentDTO);

}
