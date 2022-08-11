package com.ktds.zipzero.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.comment.mapper.CommentMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CommentServiceImpl implements CommentService{
    @Setter(onMethod_ = @Autowired)
    private CommentMapper commentMapper;

    @Override
    public List<CommentDTO> getCommentList(long pid, int skip, int size) {

        List<CommentDTO> commentList = commentMapper.getPage(pid, skip, size);

        return commentList;

    }
    
}