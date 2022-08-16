package com.ktds.zipzero.comment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    private Long cid;

    private String ccontent;
    private LocalDateTime cregdate;
    private LocalDateTime cmoddate;
    private int ccheck;

    private Long mid;
    private Long pid;
}
