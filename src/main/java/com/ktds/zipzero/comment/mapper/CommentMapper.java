package com.ktds.zipzero.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface CommentMapper {
    
    @Select("")
    void insert(CommentDTO commentDTO);

    @Select("")
    void delete(CommentDTO commentDTO);

    @Select("")
    List<CommentDTO> getPage(PaymentDTO paymentDTO, PageDTO pageDTO);

    @Select("")
    void modify(CommentDTO commentDTO);
}
