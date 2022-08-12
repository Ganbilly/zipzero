package com.ktds.zipzero.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface CommentMapper {
    
    
    public int write(CommentDTO commentDTO);

    @Select("")
    void delete(CommentDTO commentDTO);

    
    public List<CommentDTO> getCommentList(@Param("pid") long pid, @Param("skip") int skip, @Param("size") int size);

    @Select("")
    void modify(CommentDTO commentDTO);
}
