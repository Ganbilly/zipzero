package com.ktds.zipzero.mapper;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.member.dto.MemberRole;
import com.ktds.zipzero.member.mapper.MemberMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberTests {

    @Autowired(required = false)
    MemberMapper mapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void getMemberByIdTests() {
        Long id = 3L;
        log.info(mapper.getMemberById(id));

    }

    @Test
    public void getMemberAuth() {
        long mid = 1L;
        log.info(Stream.of(MemberRole.values()).map(Enum::name).collect(Collectors.toList()));
    }

    @Test
    public void changePWencoder() {
        for(int i = 2; i <= 107; i++) {
            MemberDTO memberDTO = mapper.getMemberById((long)i);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            memberDTO.setMpw(encoder.encode(memberDTO.getMpw()));
            mapper.setMember(memberDTO);
        }
    }
}
