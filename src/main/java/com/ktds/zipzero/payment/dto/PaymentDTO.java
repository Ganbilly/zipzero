package com.ktds.zipzero.payment.dto;

import java.time.LocalDateTime;

import com.ktds.zipzero.member.dto.MemberDTO;

import lombok.Data;

@Data
public class PaymentDTO {
    
    private Long pid;


    private String pname;

    private LocalDateTime ptime;

    private LocalDateTime pregdate;

    private LocalDateTime pmoddate;

    private String pstorename;

    private Long ptotalprice;

    private boolean pcardtype;

    private String preceipt;

    private boolean pcheck;

    private Long pcurstate;

    private Long pfinstate;

    
    private StateDTO stateDTO;

    private MemberDTO memberDTO;

    private PaytypeDTO paytypeDTO;
}
