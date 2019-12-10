package com.gemini.gembook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gemini.gembook.model.Comment;
import com.gemini.gembook.service.CommentService;

@RestController
@RequestMapping(value="/GemBook/post/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	private final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
    @GetMapping
    public BaseResponse getAllCommentsByPostId(@RequestParam(value = "postId") int postId){
        List<Comment> comments =commentService.getCommentsByPostId(postId);

        if(comments == null) {
        	logger.info("comments not found.");
            return new BaseResponse("Internal Error", HttpStatus.NOT_FOUND,null);        
        }

        logger.info("comments retrieved");
        return new BaseResponse("Success",HttpStatus.OK, comments);
    }
	
    @PostMapping
    public BaseResponse addComment(@RequestParam(value = "postId") int postId,
    		@RequestParam(value = "userId") String userId, @RequestParam(value = "commentContent") String commentContent){
    	logger.info("addComment method hit");
    	Comment comment = new Comment(postId, userId,commentContent);
    	comment = commentService.addComment(comment);
        if(comment == null) {
            logger.warn("comment : {} not added",comment);
            return new BaseResponse("Failure", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        logger.info("comment created : {}",comment);
        return new BaseResponse("Success",HttpStatus.CREATED,comment);
    }
    
    @DeleteMapping
    public BaseResponse deleteComment(@RequestParam(value = "commentId")int commentId){
        
    	if(commentService.findByCommentId(commentId) == null){
            logger.warn("Comment does not exists : {}",commentId);
            return new BaseResponse("Comment does not exists",HttpStatus.NOT_ACCEPTABLE,false);
        }

        if(commentService.deleteComment(commentId)) {
            logger.info("Comment deleted : {}",commentId);
            return new BaseResponse("Success", HttpStatus.OK, true);
        }

        logger.warn("Comment not deleted {}",commentId);
        return new BaseResponse("Failure",HttpStatus.INTERNAL_SERVER_ERROR,false);
    
    }

}
