package com.ktds.zipzero.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.comment.mapper.CommentMapper;

@SpringBootTest
public class CommentTests {


    @Autowired(required = false)
    CommentMapper commentMapper;

    @Test
    public void testGetComment() {
        PageDTO pageDTO = PageDTO.builder().page(1).size(5).build();
        commentMapper.getPage(2L, pageDTO.getSkip(), pageDTO.getSize());


    }


}
