package com.ktds.zipzero.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.comment.mapper.CommentMapper;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class CommentTests {


    @Autowired(required = false)
    CommentMapper commentMapper;

    @Test
    public void testGetComment() {
        PageDTO pageDTO = PageDTO.builder().page(1).size(5).build();
        commentMapper.getCommentList(2L, pageDTO.getSkip(), pageDTO.getSize());

    }

    @Test
    public void testWrite() {
        CommentDTO comment = new CommentDTO();

        
        log.info(comment);
        
    }





}
