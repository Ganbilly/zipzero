package com.ktds.zipzero.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.ktds.zipzero.member.dto.AuthDTO;
import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.member.dto.MemberRole;

@Repository
public interface MemberMapper {
    @Select("")
    public void login(MemberDTO memberDTO);

    @Select("")
    public void logout(MemberDTO memberDTO);

    /*
     * 만든사람 : 이은성(2022-08-10)
     * 최종수정 : 이은성(2022-08-10)
     * 기능 : ID로 MemberDTO 찾아서 반환
     */
    public MemberDTO getMemberById(@Param("mid") Long mid);

    public List<AuthDTO> getMemberAuth(@Param("mid") long mid);

    public void setMember(@Param("member") MemberDTO memberDTO);
}
