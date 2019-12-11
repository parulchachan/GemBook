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

import com.gemini.gembook.model.Post;
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
        List<Post> posts = (List<Post>) postService.getAllPosts();

        if(posts == null) {
        	logger.warn("post not found.");
            return new BaseResponse("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR,null);
            
        }

        logger.info("Posts retrieved");
        return new BaseResponse("Success",HttpStatus.OK, posts);
    }
	
	
    @PostMapping
    public BaseResponse addPost(@RequestParam(value = "post_type_id") int postTypeId,
    		@RequestParam(value = "user_name") String userName, @RequestParam(value = "post_content") String postContent){
    	
        Post post = postService.addPost(postTypeId, userName, postContent);
        if(null == post) {
            logger.warn("post : {} not added",postContent);
            return new BaseResponse("Failure", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        logger.info("post created : {}",post);
        return new BaseResponse("Success",HttpStatus.CREATED,post);
    }
	
    @GetMapping(value="/postbyuser")
    public BaseResponse getUserPost(@RequestParam(value="user_name") String userName) {
    	List<Post> posts = postService.getPostByUser(userName);
    	if(null == posts || posts.isEmpty()) {
    		logger.warn("no posts found for user : {} ", userName);
    		return new BaseResponse("No posts found or invalid user_name", HttpStatus.INTERNAL_SERVER_ERROR, null);
    	}
    	else {
    		logger.info("posts retrieved");
    		return new BaseResponse("Success",HttpStatus.OK, posts);
    	}
    	
    }
    
    @GetMapping(value="/postbytype")
    public BaseResponse getPostByType(@RequestParam(value="post_type_id") int postTypeId) {
    	List<Post> posts = postService.getPostByType(postTypeId);
    	if(null == posts || posts.isEmpty()) {
    		logger.warn("no posts found with type : {} ", postTypeId);
    		return new BaseResponse("No posts found or invalid user post_type_id", HttpStatus.INTERNAL_SERVER_ERROR, null);
    	}
    	else {
    		logger.info("posts retrieved");
    		return new BaseResponse("Success",HttpStatus.OK, posts);
    	}
    	
    }
	
    @DeleteMapping
    public BaseResponse deletePost(@RequestParam(value="post_id") int postId) {
    	
    	if(postService.deletePost(postId)) {
    		logger.info("post deleted successfully.");
    		return new BaseResponse("Post deleted successfully",HttpStatus.OK,true);
    	}
    	else {
    		logger.error("post with post_id {} could not be deleted",postId);
    		return new BaseResponse("post could not be deleted",HttpStatus.INTERNAL_SERVER_ERROR,
    				false);
    	}
    }
    
    @PutMapping
	public BaseResponse editPost(@RequestParam("post_id")int postId, @RequestParam("post_content") String postContent) {
		if(postService.updatePost(postId, postContent)) {
			return new BaseResponse("post updated successfully",HttpStatus.OK,true);
		}
		else {
			return new BaseResponse("post could not be updated",HttpStatus.INTERNAL_SERVER_ERROR, false);
		}
	}
    
    
//    @GetMapping
//    public BaseResponse PageablePost() {
//    	
//    }
    
}
