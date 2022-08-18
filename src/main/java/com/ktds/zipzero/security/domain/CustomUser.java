package com.ktds.zipzero.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.member.dto.MemberRole;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class CustomUser extends User {

	private static final long serialVersionUID = 1L;

	private MemberDTO member;

	public CustomUser(String username, String password, 
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUser(MemberDTO memberDTO) {
		super(memberDTO.getEmpno(), memberDTO.getMpw(),
			memberDTO.getAuthList().stream().map(auth -> new SimpleGrantedAuthority("ROLE_" + auth.getAuthname())).collect(Collectors.toList()));	
		// Stream.of(memberDTO.getAuthList()).map(roleName -> new SimpleGrantedAuthority("ROLE_"+roleName)).collect(Collectors.toList()));
		log.info("getAuthorities============================ " + this.getAuthorities());

		this.member = memberDTO;
		log.info("member===================== " + member);
	}
}
