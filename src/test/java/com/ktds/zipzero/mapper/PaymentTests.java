package com.ktds.zipzero.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.member.mapper.MemberMapper;
import com.ktds.zipzero.payment.dto.FilterDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;
import com.ktds.zipzero.payment.mapper.PaymentMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class PaymentTests {
    @Autowired(required = false)
    PaymentMapper paymentMapper;

    @Autowired(required = false)
    MemberMapper memberMapper;

    /*
     * 만든 사람 : 정문경
     * 최종 수정 : 정문경
     * 기능 : mid와 pid가 일치하는 payment 데이터들을 (page - 1) * size부터 10개 가져옴
     */
    @Test
    public void testGetPage() {
        PageDTO pageDTO = PageDTO.builder().page(1).size(10).build();
        paymentMapper.getUserPage(2L, pageDTO.getSkip(), pageDTO.getSize());
    }

    /*
     * 만든 사람 : 정문경
     * 최종 수정 : 정문경
     * 기능 : 필터 조건에 해당하는 페이지 목록 춫력
     */
    @Test
    public void testGetFilterPage() {
        FilterDTO filterDTO = FilterDTO.builder().endTime(LocalDate.now()).mid("3").build();
        List<FilterDTO> filterList = paymentMapper.getAdminPage(filterDTO, 0, 10);
        filterList.forEach(f -> log.info(f));
    }

    /*
     * 만든 사람 : 정문경
     * 최종 수정 : 정문경
     * 기능 : pid가 맞는 영수증 정보 가져옴
     */

    @Test
    public void testGetDetail() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPid(2L);
        log.info(paymentMapper.getDetail(paymentDTO));
    }

    /*
     * 만든사람 : 이은성
     * 최종수정 : 이은성
     * 기능 : paymentRegist 테스트
     */
    @Test
    public void testRegistPayment() {
        PaymentDTO paymentDTO = new PaymentDTO();

        paymentDTO.setPname("test입니다");
        paymentDTO.setPtime(LocalDateTime.now());
        paymentDTO.setPregdate(LocalDateTime.now());
        paymentDTO.setPmoddate(LocalDateTime.now());
        paymentDTO.setPstorename("양갈비집");
        paymentDTO.setPtotalprice(100000L);
        paymentDTO.setPcardtype(1);
        paymentDTO.setPreceipt("test.png");
        paymentDTO.setMid(1L);
        paymentDTO.setSid(2L);
        paymentDTO.setPtypecode(1L);
        paymentDTO.setPcurstate(1L);
        paymentDTO.setPfinstate(1L);

        paymentMapper.registPayment(paymentDTO);
        log.info(paymentMapper.getDetail(paymentDTO));
    }

    /*
     * 만든 사람 : 정문경
     * 최종 수정 : 정문경
     * 기능 : pid가 맞는 영수증 데이터의 p_check를 0으로 변경
     */
    @Test
    public void testDeletePayment() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPid(2L);
        paymentMapper.deletePayment(paymentDTO);
    }

    @Test
    public void testModifyPayment() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPname("p_name"); // 결제내역
        paymentDTO.setPtime(LocalDateTime.now().plusDays(2)); // 결제시각
        paymentDTO.setPmoddate(LocalDateTime.now()); // 결제건수정시간
        paymentDTO.setPstorename("p_storename"); // 결제 상호명
        paymentDTO.setPtotalprice(12345L); // 결제총액
        paymentDTO.setPcardtype(1); // 결제카드유형
        paymentDTO.setPreceipt("p_receipt"); // 영수증
        paymentDTO.setPcurstate(1L); // 삭제여부
        paymentDTO.setPfinstate(3L); // 최종결제상태
        paymentDTO.setSid(3L); // 상태아이디
        paymentDTO.setPtypecode(2L); // 회원아이디
        paymentDTO.setPid(2L);
        paymentMapper.modifyPayment(paymentDTO);
    }

    @Test
    public void testGetPageByPid() {
        List<PaymentDTO> paymentList = paymentMapper.getPageByPid(2L, 1, 10);
        paymentList.forEach(p -> log.info(p.getPname()));
    }

    @Test
    public void testLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info(LocalDateTime.parse("2020-10-30T10:30:20".replace("T", " "), formatter));
    }
}
