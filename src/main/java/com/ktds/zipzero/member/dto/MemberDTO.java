package com.ktds.zipzero.member.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private Long mid;
    
    private String mpw;
    private String mname;
    private boolean mcekck;

    private AuthDTO authDTO;
    private HeadQuaterDTO headQuaterDTO;
    private DepartmentDTO departmentDTO;
    private TeamDTO teamDTO;

}
