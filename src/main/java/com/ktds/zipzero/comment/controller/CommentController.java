package com.ktds.zipzero.comment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ktds.zipzero.comment.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/comment/*")
@RequiredArgsConstructor
@Log4j2
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/delete")
    public String commentDelete(Long cid, Long pid) {
        commentService.removeCommentByCid(cid);

        return "redirect:/payment/admindetail?pid=" + pid;
    }


    @GetMapping("/modify")
    public void commentModify() {
        
    }
    
}