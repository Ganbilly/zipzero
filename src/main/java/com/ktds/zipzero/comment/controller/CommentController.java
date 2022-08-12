package com.ktds.zipzero.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ktds.zipzero.all.dto.PageDTO;
import com.ktds.zipzero.comment.dto.CommentDTO;
import com.ktds.zipzero.comment.service.CommentService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
@Log4j2
public class CommentController {

    private CommentService commentService;

    /*
    * 만든사람 : 박유진(2022-08-12)
    * 최종수정 : 박유진(2022-08-12)
    * 기능 : /comment/write 페이지에서 댓글 등록
    */
    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody CommentDTO commentDTO) {
        
        boolean result = commentService.write(commentDTO);
        return result == true ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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