package com.ktds.zipzero.payment.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.zipzero.comment.dto.CommentDTO;
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
    public long getMidByPid(long pid) {
        return paymentMapper.getMidByPid(pid);
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

    /*
     * 만든 사람 : 김예림(2022-08-10)
     * 최종 수정 : 김예림(2022-08-12)
     * 기능 : 본인 소속의 모든 직원 영수증 내역 조회
     */
    @Override
    public List<PaymentDTO> getAuthList(long mid) {
        List<PaymentDTO> adminpaymentList = paymentMapper.getMidListByAuth(mid);
        return adminpaymentList;

    }
    /*
     * 만든 사람 : 정문경(2022-08-16)
     * 최종 수정 : 정문경(2022-08-16)
     * 기능 : 본인 소속의 모든 직원 영수증 내역 조회
     */
    @Override
    public List<PaymentDTO> getAuthPage(long mid, int skip, int size) {
        List<PaymentDTO> adminpaymentList = paymentMapper.getMidPageByAuth(mid, skip, size);
        return adminpaymentList;

    }

    /*
     * 만든 사람 : 정문경(2022-08-12)
     * 최종 수정 : 정문경(2022-08-12)
     * 기능 : 댓글 등록
     */
    @Override
    public void registComment(CommentDTO commentDTO) {
        paymentMapper.registComment(commentDTO);
    }


    /*
    * 만든 사람 : 정문경(2022-08-12)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : 필터링된 영수증 전체 목록
    */
    @Override
    public List<FilterDTO> getAllPaymentFilter(FilterDTO filterDTO) {
        return paymentMapper.getAllAdminPage(filterDTO);
    }

    /*
    * 만든 사람 : 정문경(2022-08-16)
    * 최종 수정 : 정문경(2022-08-16)
    * 기능 : 본인 영수증 전체 목록
    */
    public List<PaymentDTO> getAllPaymentList(long mid) {
        return paymentMapper.getAllUserPage(mid);
    }
}
