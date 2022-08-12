package com.ktds.zipzero.comment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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


    @PostMapping("/write")
    public ResponseEntity<String> write(@RequestBody CommentDTO commentDTO) {
        
        boolean result = commentService.write(commentDTO);
        return result == true ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/delete")
    public void commentDelete() {

    }

    @GetMapping("/list")
    public String getCommentList(Model model, @RequestParam(value = "pid") long pid, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "100") int size){
        
        
        log.info("commentList");
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        List<CommentDTO> commentList = commentService.getCommentList(pid, pageDTO.getSkip(), size);

        model.addAttribute("commentList", commentService.getCommentList(pid, 1, 100));
        
        return "comment/list";
    }


    @PostMapping("/modify")
    public String commentModify(Model model, @ModelAttribute("pid") long pid) {

        model.addAttribute("comments", commentService.getCommentList(pid, 1, 5));

        return "comment/modify";
    }

    @PostMapping("/modifyresult")
    public String paymentModifyResult(Model model, @ModelAttribute("paymentDTO") CommentDTO commentDTO) {
        log.info("PaymentModifyResult");
        commentDTO.setCmoddate(LocalDateTime.now());
        commentDTO.setCcontent("안녕하세요");
        commentService.modifyComment(commentDTO);
        
        // model.addAttribute("mid", commentService.getPaymentDetail(commentDTO.getPid()).getMid());

        return "redirect:userlist";
    }
    
}