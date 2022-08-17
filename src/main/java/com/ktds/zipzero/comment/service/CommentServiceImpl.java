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
    public Long registerComment(CommentDTO commentDTO) {


        
        
        commentMapper.registerComment(commentDTO);
        
        return commentDTO.getPid();
        
    }

    @Override
    public void modifyComment(CommentDTO commentDTO){
        commentMapper.modifyComment(commentDTO);
    }

    @Override
    public void deleteComment(Long cid) {
        
        
    }


   
    
}
