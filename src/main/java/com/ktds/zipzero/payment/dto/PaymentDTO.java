package com.ktds.zipzero.payment.dto;

import java.time.LocalDateTime;


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
    private Integer pcardtype;
    private String preceipt;
    private int pcheck;
    private Long pcurstate;
    private Long pfinstate;

    private Long sid;
    private Long mid;
    private Long ptypecode;
}
