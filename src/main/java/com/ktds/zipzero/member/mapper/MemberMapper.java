package com.ktds.zipzero.member.mapper;

import org.apache.ibatis.annotations.Select;

import com.ktds.zipzero.member.dto.MemberDTO;

public interface MemberMapper {
    @Select("")
    public void login(MemberDTO memberDTO);

    @Select("")
    public void logout(MemberDTO memberDTO);
    
}
