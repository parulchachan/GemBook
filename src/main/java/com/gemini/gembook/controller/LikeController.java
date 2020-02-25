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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gemini.gembook.model.Like;
import com.gemini.gembook.model.LikeIdentity;
import com.gemini.gembook.repository.LikeRepository;
import com.gemini.gembook.repository.PostRepository;
import com.gemini.gembook.repository.UsersRepository;
import com.gemini.gembook.service.LikeService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value="/GemBook/post/like")
public class LikeController {
	
	
	@Autowired
	private LikeService likeService;
	
	private final Logger logger = LoggerFactory.getLogger(LikeController.class);
	
	@GetMapping
    public BaseResponse getLikesByPostId(@RequestParam(value = "postId") int postId){
        List<Like> likes = likeService.getLikesByPostId(postId);

        if(likes == null) {
        	logger.info("likes not found.");
            return new BaseResponse("Internal Error", HttpStatus.NOT_FOUND,null);
        }
        logger.info("likes retrieved");
        return new BaseResponse("Success",HttpStatus.OK, likes);
    }
	
	@PostMapping
    public BaseResponse saveLike(@RequestParam(value = "postId") int postId,
    		@RequestParam(value = "userId") String userId,
    		@RequestParam(value = "likeFlag") String likeFlag,HttpServletResponse response){
    	logger.info("saveLike method hit");
    	
    	if(!(likeFlag.equals("Y") || likeFlag.equals("N"))){
    		return new BaseResponse("Wrong Error flag", HttpStatus.NOT_ACCEPTABLE, null);
    	}
    	Like like = new Like(new LikeIdentity(postId,userId),likeFlag);
    	if(likeService.getLike(postId, userId) != null) {
    		if(likeService.updateLike(postId,userId,likeFlag,like.getLikeTime())) {
    			response.setStatus(HttpServletResponse.SC_CREATED);
    			return new BaseResponse("Like updated",HttpStatus.CREATED,like);
    		}else {
    			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    			return new BaseResponse("Like not updated.", HttpStatus.INTERNAL_SERVER_ERROR, like);
    		}
    	}
    	like = likeService.saveLike(like);        ;
        if(like == null) {
        	if(!likeService.isLikeIdentityValid(postId,userId)) {
        		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        		return new BaseResponse("PostId or UserID invalid", HttpStatus.BAD_REQUEST, null);
        	}
            logger.warn("like : {} not added",like);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new BaseResponse("Like not added", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        logger.info("like added : {}",like);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return new BaseResponse("Like added successfully",HttpStatus.CREATED,like);
    	
    }
    
}
