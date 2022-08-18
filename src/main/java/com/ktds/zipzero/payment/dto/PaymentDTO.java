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
    private String hname;
    private String dname;
    private String tname;
    private String m_id;
    private String mname;
    private Long tprice;

    private String preceiptmonth;
    private String preceiptdate;

    private Long sid;
    private Long mid;
    private Long ptypecode;
}
