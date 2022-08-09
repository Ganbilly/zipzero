package com.ktds.zipzero.payment;

import java.time.LocalDateTime;

import com.ktds.zipzero.member.MemberDTO;

import lombok.Data;

@Data
public class PaymentDTO {
    
    private Long p_id;


    private String p_name;

    private LocalDateTime p_time;

    private LocalDateTime p_regdate;

    private LocalDateTime p_moddate;

    private String p_storename;

    private Long p_totalprice;

    private boolean p_cardtype;

    private String p_receipt;

    private boolean p_check;

    private Long p_curstate;

    private Long p_finstate;

    
    private StateDTO stateDTO;

    private MemberDTO memberDTO;

    private PaytypeDTO paytypeDTO;
}
