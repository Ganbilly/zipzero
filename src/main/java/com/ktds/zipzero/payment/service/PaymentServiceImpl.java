package com.ktds.zipzero.payment.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.stereotype.Service;

import com.ktds.zipzero.payment.dto.FilterDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/*
 * 만든 사람 : 정문경(2022-08-10)
 * 최종 수정 : 이은성(2022-08-11)
 * 기능 : 페이지 맵퍼
 */

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {
    @Setter(onMethod_ = @Autowired)
    private PaymentMapper paymentMapper;
    
    @Override
    public List<PaymentDTO> getPaymentList(long mid, int skip, int size) {
        List<PaymentDTO> paymentList = paymentMapper.getUserPage(mid, skip, size);
        return paymentList;
    }

    @Override
    public List<FilterDTO> getPaymentFilterList(FilterDTO filterDTO, int skip, int size) {
        List<FilterDTO> filterList = paymentMapper.getAdminPage(filterDTO, skip, size);
        return filterList;
    }

    @Override
    public PaymentDTO getPaymentDetail(long pid) {
        return paymentMapper.getPaymentByPid(pid);
    }

    @Override
    public List<PaymentDTO> getPaymentListByPid(long pid, int skip, int size) {
        return paymentMapper.getPageByPid(pid, skip, size);
    }

    @Override
    public String getMnameByMid(long mid) {
        return paymentMapper.getMnameByMid(mid);
    }

    @Override
    public void modifyPayment(PaymentDTO paymentDTO) {
        paymentMapper.modifyPayment(paymentDTO);
    }

    /*
    * 만든 사람 : 이은성(2022-08-11)
    * 최종 수정 : 이은성(2022-08-11)
    * 기능 : 페이먼트 등록
    */
    @Override
    public void registPayment(PaymentDTO paymentDTO) {
        paymentMapper.registPayment(paymentDTO);
    }
}
