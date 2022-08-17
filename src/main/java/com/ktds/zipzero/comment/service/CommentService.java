package com.ktds.zipzero.comment.service;

import com.ktds.zipzero.comment.dto.CommentDTO;

public interface CommentService {

    /*
     * 만든 사람 : 정문경(2022-08-12)
     * 최종 수정 : 정문경(2022-08-12)
     * 기능 : 댓글 등록
     */
    public void registComment(CommentDTO commentDTO);
    
    public void getCommentsByPid(Long pid);
}
