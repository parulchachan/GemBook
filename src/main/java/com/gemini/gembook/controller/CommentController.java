package com.gemini.gembook.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.gembook.model.Comment;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.service.CommentService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value="/dashboard/post/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	private final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@GetMapping(value="/allcomments")
	public BaseResponse getAllComments(@RequestParam("postId") int postId) {
		List<Comment> comments = commentService.getAllComments(postId);
		if(null == comments) {
			logger.error("comments not found");
			return new BaseResponse("comments not found",HttpStatus.INTERNAL_SERVER_ERROR,null);
		}
		return new BaseResponse("comments found",HttpStatus.OK,comments);
	}	 
	
	 @GetMapping(value="/commentbyid")
	    public BaseResponse getCommentId(@RequestParam(value="commentId") int commentId,
	    		HttpServletResponse response) {
	    	Comment comment = commentService.findByCommentId(commentId);
	    	if(null == comment) {
	    		logger.warn("Wrong commentId", commentId);
	    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    		return new BaseResponse("invalid userId", HttpStatus.BAD_REQUEST, null);
	    	}
	    	else {
	    		logger.info("comment retrieved");
	    		response.setStatus(HttpServletResponse.SC_OK);
	    		return new BaseResponse("Success",HttpStatus.OK, comment);
	    	}
	    }
	
	@PostMapping
	public BaseResponse addComment(@RequestParam(value = "postId") int postId,
			@RequestParam(value = "userId") String userId, @RequestParam(value = "commentContent") String commentContent){
		Comment comment = commentService.addComment(postId, userId,commentContent);
		if(null == comment) {
			logger.warn("comment : {} not added",commentContent);
			return new BaseResponse("Failure", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
		logger.info("comment created : {}",comment);
		return new BaseResponse("Success",HttpStatus.CREATED,comment);
	}
	
	@DeleteMapping
    public BaseResponse deleteComment(@RequestParam(value = "commentId")int commentId){

        if(commentService.deleteComment(commentId)) {
            logger.info("Comment deleted : {}",commentId);
            return new BaseResponse("Success", HttpStatus.OK, true);
        }

        logger.warn("Comment not deleted {}",commentId);
        return new BaseResponse("Failure",HttpStatus.INTERNAL_SERVER_ERROR,false);
    
    }
	
	@PutMapping
	public BaseResponse editComment(@RequestParam("commentId")int commentId, @RequestParam("commentContent") String commentContent) {
		if(commentService.updateComment(commentId, commentContent)) {
			return new BaseResponse("comment updated successfully",HttpStatus.OK,true);
		}
		else {
			return new BaseResponse("comment could not be updated",HttpStatus.INTERNAL_SERVER_ERROR, false);
		}
	}
	
	@GetMapping(value="/topfivecomments")
	public BaseResponse getLatestComments(@RequestParam("postId")int postId) {
		List<Comment> comments = commentService.getLatestComments(postId);
		if(null == comments) {
			logger.error("comments not found.");
			return new BaseResponse("comments not found",HttpStatus.BAD_REQUEST,null);
		}
		return new BaseResponse("comments found",HttpStatus.OK,comments);
	}
	
	
	
}
