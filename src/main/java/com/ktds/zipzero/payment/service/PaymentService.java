package com.ktds.zipzero.payment.service;

import java.util.List;

import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.payment.dto.FilterDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

    /*
    * 만든 사람 : 정문경(2022-08-10)
    * 최종 수정 : 이은성(2022-08-11)
    * 기능 : 페이먼트 서비스
    */
    public interface PaymentService {
    /*
    * 만든 사람 : 정문경(2022-08-12)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : 영수증 목록 불러오기
    */
    public List<PaymentDTO> getPaymentList(long mid, int skip, int size);

    /*
    * 만든 사람 : 정문경(2022-08-12)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : 영수증 목록 조건에 맞춰 필터링
    */
    public List<FilterDTO> getPaymentFilterList(FilterDTO filterDTO, int skip, int size);

    /*
    * 만든 사람 : 정문경(2022-08-12)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : pid로 영수증 조회
    */
    public PaymentDTO getPaymentDetail(long pid);

    /*
    * 만든 사람 : 정문경(2022-08-12)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : pid로 영수증 목록 조회
    */
    public List<PaymentDTO> getPaymentListByPid(long pid, int skip, int size);

    /*
    * 만든 사람 : 정문경(2022-08-12)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : 영수증 추가
    */
    public void modifyPayment(PaymentDTO paymentDTO);

    /*
    * 만든 사람 : 정문경(2022-08-12)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : mid로 이름 조회
    */
    public String getMnameByMid(long mid);


    /*
    * 만든 사람 : 정문경(2022-08-12)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : pid로 mid 조회
    */
    public long getMidByPid(long pid);

    /*
    * 만든 사람 : 이은성(2022-08-11)
    * 최종 수정 : 이은성(2022-08-11)
    * 기능 : 페이먼트 등록
    */
    public void registPayment(PaymentDTO paymentDTO);

    /*
    * 만든 사람 : 김예림(2022-08-10)
    * 최종 수정 : 김예림(2022-08-12)
    * 기능 : 본인 소속의 모든 직원 영수증 내역 조회
    */
    public List<PaymentDTO> getAuthList(long mid);

    /*
    * 만든 사람 : 정문경(2022-08-10)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : 본인 소속의 직원 영수증 내역 조회
    */
    public List<PaymentDTO> getAuthPage(long mid, int skip, int size);

    /*
    * 만든 사람 : 정문경(2022-08-12)
    * 최종 수정 : 정문경(2022-08-12)
    * 기능 : 필터링된 영수증 전체 목록
    */
    public List<FilterDTO> getAllPaymentFilter(FilterDTO filterDTO);

    /*
    * 만든 사람 : 정문경(2022-08-16)
    * 최종 수정 : 정문경(2022-08-16)
    * 기능 : 본인 영수증 전체 목록
    */
    public List<PaymentDTO> getAllPaymentList(long mid);

    /*
     * 만든 사람 : 이은성(2022-08-18)
     * 최종 수정 : 이은성(2022-18-18)
     * 기능 : 메인페이지에서 관리 리스트 5개 출력(최신순으로 정렬)
     */
    public List<PaymentDTO> getAdminPaymentsForMain(Long mid);
    
    /*
     * 만든 사람 : 이은성(2022-08-18)
     * 최종 수정 : 이은성(2022-18-18)
     * 기능 : 메인페이지에서 내 payment 5개 출력(최신순으로 정렬)
     */
    public List<PaymentDTO> getMyPaymentsForMain(Long mid);


}
