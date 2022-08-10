package com.ktds.zipzero.member.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private Long mid;
    
    private String mpw;
    private String mname;
    private boolean mcheck;
  
    private Long aid;
    private Long hqid;
    private Long did;
    private Long tid;

}
