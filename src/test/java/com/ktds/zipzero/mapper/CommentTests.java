package com.ktds.zipzero.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.comment.mapper.CommentMapper;
import com.ktds.zipzero.member.dto.MemberDTO;
import com.ktds.zipzero.member.mapper.MemberMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CommentTests {
    
    @Autowired(required = false)
    CommentMapper commentMapper;

    @Autowired(required = false)
    MemberMapper memberMapper;

    @Test
    public void CommentListTests() {
        Long pid = 123L;
        
        log.info(commentMapper.getCommentsByPid(pid));

    }  
}
