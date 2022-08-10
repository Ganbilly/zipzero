package com.ktds.zipzero.member.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private Long mid;
    
    private String mpw;
    private String mname;
    private boolean mcheck;

    private AuthDTO authDTO;
    private HeadQuarterDTO headQuaterDTO;
    private DepartmentDTO departmentDTO;
    private TeamDTO teamDTO;

}
