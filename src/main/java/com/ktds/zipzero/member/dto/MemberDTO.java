package com.ktds.zipzero.member.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private Long mid;
    
    private String mpw;
    private String mname;
    private boolean mcheck;
  
    private Long authid;
    private Long hqid;
    private Long deptid;
    private Long teamid;
}
