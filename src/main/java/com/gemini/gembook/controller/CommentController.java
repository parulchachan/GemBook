package com.gemini.gembook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.gembook.model.Comment;
import com.gemini.gembook.service.CommentService;

@RestController
@RequestMapping(value="/dashboard/comment")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@GetMapping(value="/allcomments")
	public BaseResponse getAllComments(@RequestParam("post_id") int postId) {
		List<Comment> comments = commentService.getAllComments(postId);
		if(null == comments) {
			logger.error("comments not found");
			return new BaseResponse("comments not found",HttpStatus.INTERNAL_SERVER_ERROR,null);
		}
		return new BaseResponse("comments found",HttpStatus.OK,comments);
	}
	
	@PostMapping
	public BaseResponse addComment(@RequestParam("post_id") int postId, @RequestParam("user_name") String userName,
			@RequestParam("comment_content") String commentContent) {
		Comment comment = commentService.insertComment(postId, userName, commentContent);
		if(null == comment) {
			logger.error("comment not added successfully");
			return new BaseResponse("comment not added successfully",HttpStatus.INTERNAL_SERVER_ERROR,null);
		}
		return new BaseResponse("comment added successfully",HttpStatus.OK,comment);
	}
	
	@DeleteMapping
	public BaseResponse deleteComment(@RequestParam("comment_id") int commentId) {
		if(commentService.deleteComment(commentId)) {
			return new BaseResponse("comments deleted successfully",HttpStatus.OK,true);
		}
		else {
			return new BaseResponse("comment could not be deleted", HttpStatus.INTERNAL_SERVER_ERROR, false);
		}
	}
	
	@PutMapping
	public BaseResponse editComment(@RequestParam("comment_id")int commentId, @RequestParam("comment_content") String commentContent) {
		if(commentService.updateComment(commentId, commentContent)) {
			return new BaseResponse("comment updated successfully",HttpStatus.OK,true);
		}
		else {
			return new BaseResponse("comment could not be updated",HttpStatus.INTERNAL_SERVER_ERROR, false);
		}
	}
	
	@GetMapping(value="/topfivecomments")
	public BaseResponse getLatestComments(@RequestParam("post_id")int postId) {
		List<Comment> comments = commentService.getLatestComments(postId);
		if(null == comments) {
			logger.error("comments not found.");
			return new BaseResponse("comments not found",HttpStatus.INTERNAL_SERVER_ERROR,null);
		}
		return new BaseResponse("comments found",HttpStatus.OK,comments);
	}
	
}
