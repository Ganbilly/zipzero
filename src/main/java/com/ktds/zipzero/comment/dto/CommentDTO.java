package com.ktds.zipzero.comment.dto;

import java.time.LocalDateTime;

import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

import lombok.Data;

@Data
public class CommentDTO {
    private Long cid;

    private String ccontent;
    private LocalDateTime cregdate;
    private LocalDateTime cmoddate;
    private boolean ccheck;

    private MemberDTO memberDTO;
    private PaymentDTO paymentDTO;
}
