package com.gemini.gembook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gemini.gembook.model.Post;
import com.gemini.gembook.model.PostType;
import com.gemini.gembook.model.User;
import com.gemini.gembook.service.PostService;

@RestController
@RequestMapping(value="/dashboard/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
    /*
    Returns a list of all users
    */
    @GetMapping(value = "/allposts")
    public BaseResponse getAllPosts(){
        List<Post> posts = (List<Post>) postService.getPosts();

        if(posts == null) {
        	logger.info("post not found.");
            return new BaseResponse("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR,null);
            
        }

        logger.info("Posts retrieved");
        return new BaseResponse("Success",HttpStatus.OK, posts);
    }
	
	
    @PostMapping
    public BaseResponse addUser(@RequestParam(value = "post_type") int postType,
    		@RequestParam(value = "user_id") String userId, @RequestParam(value = "post_content") String postContent){
    	logger.info("addUser method hit");
    	User user = new User("siddharth","chhokar",userId,"Awesome2020".toCharArray());
    	PostType postTypeObj = new PostType(postType,"findings");
        Post post = postService.setPost(new Post(postTypeObj,user,postContent));        
        if(post == null) {
            logger.warn("post : {} not added",postType);
            return new BaseResponse("Failure", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        logger.info("post created : {}",post);
        return new BaseResponse("Success",HttpStatus.CREATED,post);
    }
	
	
}
