package com.ktds.zipzero.member;

import org.apache.ibatis.annotations.Select;

public interface MemberMapper {
    @Select("")
    public void login(MemberDTO memberDTO);

    @Select("")
    public void logout(MemberDTO memberDTO);
    
}
