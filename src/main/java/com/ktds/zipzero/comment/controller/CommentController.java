package com.ktds.zipzero.comment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/comment/*")
@Log4j2
public class CommentController {
    @GetMapping("/regist")
    public void commentRegist() {

    }

    @GetMapping("/delete")
    public void commentDelete() {

    }

    @GetMapping("/list")
    public void commentList() {

    }

    @GetMapping("/modify")
    public void commentModify() {
        
    }
    
}