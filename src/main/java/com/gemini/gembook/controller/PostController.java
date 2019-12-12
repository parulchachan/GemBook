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

import com.gemini.gembook.model.Post;
import com.gemini.gembook.service.PostService;

@RestController
@RequestMapping(value="/GemBook/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
    @GetMapping
    public BaseResponse getAllPosts(){
        List<Post> posts = (List<Post>) postService.getPosts();

        if(posts.isEmpty()) {
        	logger.info("post not found.");
            return new BaseResponse("Internal Error", HttpStatus.NOT_FOUND,null);        
        }

        logger.info("Posts retrieved");
        return new BaseResponse("Success",HttpStatus.OK, posts);
    }
    
    @GetMapping(value="/next")
    public BaseResponse getnextfifteenPost(@RequestParam(value = "postCount") int postCount){ 
        List<Post> posts = postService.getnextfifteenPost(postCount);
        
        if(posts == null) {
        	logger.info("post not found.");
            return new BaseResponse("Internal Error", HttpStatus.NOT_FOUND,null);
        }

        logger.info("Posts retrieved");
        return new BaseResponse("Success",HttpStatus.OK, posts);
    }
	
    @PostMapping
    public BaseResponse addPost(@RequestParam(value = "postType") int postType,
    		@RequestParam(value = "userId") String userId, @RequestParam(value = "postContent") String postContent){
    	logger.info(postType+userId+postContent);
    	logger.info("addPost method hit");
    	Post post = new Post(postType,userId,postContent);
        post = postService.addPost(post);        ;
        if(post == null) {
            logger.warn("post : {} not added",postType);
            return new BaseResponse("Failure", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        logger.info("post created : {}",post);
        return new BaseResponse("Success",HttpStatus.CREATED,post);
    }
    
    @DeleteMapping
    public BaseResponse deletePost(@RequestParam(value = "postId")int postId,@RequestParam(value = "userId") String userId){
        
    	if(postService.findByPostId(postId) == null){
            logger.warn("Post does not exists : {}",postId);
            return new BaseResponse("Post does not exists",HttpStatus.NOT_ACCEPTABLE,false);
        }

        if(postService.deletePost(postId, userId)) {
            logger.info("Post deleted : {}",postId);
            return new BaseResponse("Success", HttpStatus.OK, true);
        }

        logger.warn("Post not deleted {}",postId);
        return new BaseResponse("Failure",HttpStatus.INTERNAL_SERVER_ERROR,false);
    }
    
}
