package com.gemini.gembook.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.gembook.model.CommentIdentity;
import com.gemini.gembook.model.CommentLike;
import com.gemini.gembook.service.CommentLikeService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value="/GemBook/post/comment/like")
public class CommentLikeController {
	
	@Autowired
	private CommentLikeService commentLikeService;
	
	private final Logger logger = LoggerFactory.getLogger(CommentLikeController.class);
	
	@PostMapping
	public BaseResponse saveCommentLike(@RequestParam(value = "commentId") int commentId,
			@RequestParam(value = "userId") String userId,
    		@RequestParam(value = "commentLikeFlag") String likeFlag,HttpServletResponse response) {
		
		logger.info("saveCommentLike method hit");
    	
    	if(!(likeFlag.equals("Y") || likeFlag.equals("N"))){
    		return new BaseResponse("Wrong Error flag", HttpStatus.NOT_ACCEPTABLE, null);
    	}
    	CommentLike commentLike = new CommentLike(new CommentIdentity(commentId,userId),likeFlag);
    	if(commentLikeService.getCommentLike(commentId, userId) != null) {
    		if(commentLikeService.updateCommentLike(commentId,userId,likeFlag,commentLike.getCommentLikeTime())) {
    			response.setStatus(HttpServletResponse.SC_CREATED);
    			return new BaseResponse("Comment like updated",HttpStatus.CREATED,commentLike);
    		}else {
    			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    			return new BaseResponse("Comment like not updated.", HttpStatus.INTERNAL_SERVER_ERROR, commentLike);
    		}
    	}
    	
    	commentLike = commentLikeService.saveCommentLike(commentLike);        ;
        if(commentLike == null) {
        	if(!commentLikeService.isCommentIdentityValid(commentId,userId)) {
        		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        		return new BaseResponse("PostId or UserID invalid", HttpStatus.BAD_REQUEST, null);
        	}
            logger.warn("like : {} not added",commentLike);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new BaseResponse("Like not added", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        logger.info("like added : {}",commentLike);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return new BaseResponse("Like added successfully",HttpStatus.CREATED,commentLike);
	}
	
}
