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
import com.gemini.gembook.model.Like;
import com.gemini.gembook.model.LikeIdentity;
import com.gemini.gembook.service.LikeService;

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
    		@RequestParam(value = "userId") String userId){
    	logger.info("saveLike method hit");
    	if(likeService.getLike(postId, userId) != null) {
    		return new BaseResponse("Like already exist.", HttpStatus.NOT_ACCEPTABLE, null);
    	}
    	Like like = new Like(new LikeIdentity(postId,userId));
    	like = likeService.saveLike(like);        ;
        if(like == null) {
            logger.warn("like : {} not added",like);
            return new BaseResponse("Failure", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        logger.info("like added : {}",like);
        return new BaseResponse("Success",HttpStatus.CREATED,like);
    }
	
	@DeleteMapping
    public BaseResponse deleteLike(@RequestParam(value = "postId")int postId,@RequestParam(value = "userId") String userId){
        
		Like like = likeService.getLike(postId, userId);
    	if(like == null){
            logger.warn("like does not exists : {}",postId);
            return new BaseResponse("like does not exists",HttpStatus.NOT_ACCEPTABLE,false);
        }

        if(likeService.deleteLike(postId, userId)) {
            logger.info("like deleted : {}",postId);
            return new BaseResponse("Success", HttpStatus.OK, true);
        }

        logger.warn("like not deleted {}",postId);
        return new BaseResponse("Failure",HttpStatus.INTERNAL_SERVER_ERROR,false);
    }
    
	
}
