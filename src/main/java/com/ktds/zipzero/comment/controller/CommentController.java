package com.ktds.zipzero.comment.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.comment.service.CommentService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/comment")
@AllArgsConstructor
@Log4j2
public class CommentController {

    private CommentService commentService;

    @GetMapping("/regist")
    public void commentRegist() {

    }

    @GetMapping("/delete")
    public void commentDelete() {

    }

    @GetMapping("/list")
    public String getCommentList(Model model, @RequestParam(value = "pid") long pid, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "5") int size){
        log.info("commentList");
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        List<CommentDTO> commentList = commentService.getCommentList(pid, pageDTO.getSkip(), size);

        model.addAttribute("commentList", commentList);
        
        return "comment/list";
    }

    @GetMapping("/modify")
    public void commentModify() {
        
    }
    
}