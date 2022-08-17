package com.ktds.zipzero.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.comment.mapper.CommentMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    /*
     * 만든 사람 : 정문경(2022-08-12)
     * 최종 수정 : 이은성(2022-08-17)
     * 기능 : 댓글 등록
     */
    @Override
    public void registComment(CommentDTO commentDTO) {
        log.info(commentDTO);
        commentMapper.registComment(commentDTO);
    }

    @Override
    public void getCommentsByPid(Long pid) {
        List<CommentDTO> comments = commentMapper.getCommentsByPid(pid);

        log.info(comments);
        
    }

    
    
}
