package com.ktds.zipzero.comment;

import java.time.LocalDateTime;

import com.ktds.zipzero.member.MemberDTO;
import com.ktds.zipzero.payment.PaymentDTO;

import lombok.Data;

@Data
public class CommentDTO {
    private Long c_id;

    private String c_content;
    private LocalDateTime c_regdate;
    private LocalDateTime c_moddate;
    private boolean c_check;

    private MemberDTO memberDTO;
    private PaymentDTO paymentDTO;
}
