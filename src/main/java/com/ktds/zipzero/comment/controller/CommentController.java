package com.ktds.zipzero.comment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/comments")
@AllArgsConstructor
@Log4j2
public class CommentController {

    private final CommentService commentService;

    // @GetMapping("/payment/{p_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<List<CommentDTO>> getListByBoard(@PathVariable("pid") Long pid) {

    //     ( commentService.getCommentList(pid, 1, 100), HttpStatus.OK);


    // }


    @PostMapping("/{pid}")
    public ResponseEntity<Long> registerComment( @PathVariable Long pid,  @RequestBody CommentDTO commentDTO) {
        
        log.info("PID: " + pid);
        log.info(commentDTO);

        Long cid = commentService.registerComment(commentDTO);
        //Long cid = 232L;

        return new ResponseEntity<>(cid, HttpStatus.OK);
    }

    @GetMapping("/{rid}")
    public void deleteComment() {

    }


    // @GetMapping(value = "/{pid}")
	// public JsonObject getCommentList(@PathVariable("pid") Long pid, @ModelAttribute("params") CommentDTO params) {

	// 	JsonObject jsonObj = new JsonObject();

	// 	List<CommentDTO> commentList = commentService.getCommentList(params);
	// 	if (CollectionUtils.isEmpty(commentList) == false) {
	// 		JsonArray jsonArr = new Gson().toJsonTree(commentList).getAsJsonArray();
	// 		jsonObj.add("commentList", jsonArr);
	// 	}

	// 	return jsonObj;
	// }

    @GetMapping("/{pid}")
    public String getCommentList(Model model, @RequestParam(value = "pid") long pid, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "size", defaultValue = "100") int size){
 
        log.info("commentList");
        PageDTO pageDTO = PageDTO.builder().page(page).size(size).build();
        List<CommentDTO> commentList = commentService.getCommentList(pid, pageDTO.getSkip(), size);

        model.addAttribute("commentList", commentService.getCommentList(pid, 1, 100));
        
        return "comments/{pid}";
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