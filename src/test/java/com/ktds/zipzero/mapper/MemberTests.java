package com.ktds.zipzero.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.member.mapper.MemberMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberTests {

    @Autowired(required = false)
    MemberMapper mapper;


    @Test
    public void getMemberByIdTests(){
        Long id = 3L;
        MemberDTO memberDTO = new MemberDTO();
        log.info(mapper.getMemberById(id));

    }
}
