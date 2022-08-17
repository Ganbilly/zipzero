package com.ktds.zipzero.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.payment.dto.PaymentDTO;

public interface CommentMapper {
    
    void delete(CommentDTO commentDTO);

    List<CommentDTO> getPage(PaymentDTO paymentDTO, PageDTO pageDTO);

    /*
     * 만든사람 : 정문경(2022-08-12)
     * 최종수정 : 이은성(2022-08-17)
     * 기능 : 댓글 등록
     */
    public void registComment(CommentDTO commentDTO);

    /*
     * 만든사람 : 이은성(2022-08-17)
     * 최종수정 : 이은성(2022-08-17)
     * 기능 : 게시글 댓글 조회(mid -> mname으로 대체)
     */
    public List<CommentDTO> getCommentsByPid(Long pid);

    /*
     * 만든사람 : 이은성(2022-08-17)
     * 최종수정 : 이은성(2022-08-17)
     * 기능 : 댓글 삭제 기능(ccheck를 0으로 변경)
     */
    public void removeCommentByCid(Long cid);
}
