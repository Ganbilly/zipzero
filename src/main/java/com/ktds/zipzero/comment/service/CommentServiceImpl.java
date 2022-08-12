package com.ktds.zipzero.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.comment.mapper.CommentMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    @Setter(onMethod_ = @Autowired)
    private CommentMapper commentMapper;

    @Override
    public List<CommentDTO> getCommentList(long pid, int skip, int size) {

        List<CommentDTO> commentList = commentMapper.getCommentList(pid, skip, size);

        return commentList;

    }

    @Override
    public boolean write(CommentDTO commentDTO) {

        return false;
    }

    @Override
    public void modifyComment(CommentDTO commentDTO){
        commentMapper.modifyComment(commentDTO);
    }


   
    
}
