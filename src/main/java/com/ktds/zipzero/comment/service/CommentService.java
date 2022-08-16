package com.ktds.zipzero.comment.service;

import java.util.List;

import com.ktds.zipzero.comment.dto.CommentDTO;

public interface CommentService {
    
    public Long registerComment(CommentDTO commentDTO);

    public List<CommentDTO> getCommentList(long pid, int skip, int size);
        
    public void modifyComment(CommentDTO commentDTO);

    public void deleteComment(Long cid);

}