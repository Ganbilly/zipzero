package com.ktds.zipzero.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberMapper memberMapper;
        log.info("----------------------------------------------------------");
        log.info("CustomLoginSuccessHandler onAuthenticationSuccess ..........");
        log.info(authentication.getPrincipal());
        log.info("authentication=================== " + authentication);
        log.info("isAuthenticated=================== " + authentication.isAuthenticated());
        //MemberDTO memberSecurityDTO = (MemberDTO) authentication.getPrincipal();
        //log.info("memberSecurityDTO===================== " + memberSecurityDTO);

        //String encodedPw = memberSecurityDTO.getMpw();
        response.sendRedirect("/index");
    }
}