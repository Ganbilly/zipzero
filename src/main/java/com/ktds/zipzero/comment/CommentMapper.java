package com.ktds.zipzero.comment;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.ktds.zipzero.all.PageDTO;
import com.ktds.zipzero.payment.PaymentDTO;

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
