package com.ktds.zipzero.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.member.mapper.MemberMapper;
import com.ktds.zipzero.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService{

    @Setter(onMethod_ = {@Autowired})
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDTO memberDTO = memberMapper.getMemberByEmpno(username);
        memberDTO.setAuthList(memberMapper.getMemberAuth(memberDTO.getMid()));

        log.info("memberDTO================ " + memberDTO);

        return memberDTO == null ? null : new CustomUser(memberDTO);
    }
    
}
