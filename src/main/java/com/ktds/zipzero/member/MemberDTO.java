package com.ktds.zipzero.member;

import lombok.Data;

@Data
public class MemberDTO {
    private Long m_id;
    
    private String m_pw;
    private String m_name;
    private boolean m_cekck;

    private AuthDTO authDTO;
    private HeadQuaterDTO headQuaterDTO;
    private DepartmentDTO departmentDTO;
    private TeamDTO teamDTO;

}
