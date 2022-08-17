package com.ktds.zipzero.comment.service;

import com.ktds.zipzero.comment.dto.CommentDTO;

public interface CommentService {

    /*
     * 만든 사람 : 정문경(2022-08-12)
     * 최종 수정 : 정문경(2022-08-12)
     * 기능 : 댓글 등록
     */
    public void registComment(CommentDTO commentDTO);
    
    /*
     * 만든사람 : 이은성(2022-08-17)
     * 최종수정 : 이은성(2022-08-17)
     * 기능 : 게시글 댓글 조회(mid -> mname으로 대체)
     */
    public void getCommentsByPid(Long pid);

    /*
     * 만든사람 : 이은성(2022-08-17)
     * 최종수정 : 이은성(2022-08-17)
     * 기능 : 댓글 삭제 기능(ccheck를 0으로 변경)
     */
    public void removeCommentByCid(Long cid);
}
