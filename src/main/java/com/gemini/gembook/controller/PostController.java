package com.gemini.gembook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
	
    @GetMapping(value="allposts")
    public BaseResponse getAllPosts(){
        List<Post> posts = (List<Post>) postService.getAllPosts();

        if(posts == null) {
        	logger.warn("post not found.");
            return new BaseResponse("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
        
        if(posts.isEmpty()) {
        	logger.info("post not found.");
            return new BaseResponse("No Post Found", HttpStatus.NOT_FOUND,null);        
        }

        logger.info("Posts retrieved");
        return new BaseResponse("Success",HttpStatus.OK, posts);
    }
    
    @GetMapping(value="/nextposts")
    public BaseResponse getNextPosts(@RequestParam(value = "fstRcntPostId") int fstRcntPostId,@RequestParam(value = "scndRcntPostId") int scndRcntPostId,@RequestParam(value = "thrdRcntPostId") int thrdRcntPostId){ 
        List<Post> posts = postService.getNextPosts(fstRcntPostId, scndRcntPostId, thrdRcntPostId);
        
        if(posts == null) {
        	logger.info("post not found.");
            return new BaseResponse("Internal Error", HttpStatus.NOT_FOUND,null);
        }

        logger.info("Posts retrieved");
        return new BaseResponse("Success",HttpStatus.OK, posts);
    }
	
    @GetMapping(value="/nextpage")
    public BaseResponse getNextPostPage(@RequestParam(value = "pageNo") int pageNo){ 
        List<Post> posts = postService.getNextPostPage(PageRequest.of(pageNo, 3));
        
        if(posts == null) {
        	logger.info("post not found.");
            return new BaseResponse("Internal Error", HttpStatus.NOT_FOUND,null);
        }

        logger.info("Posts retrieved");
        return new BaseResponse("Success",HttpStatus.OK, posts);
    }
    
    @PostMapping
    public BaseResponse addPost(@RequestParam(value = "postTypeId") int postTypeId,
    		@RequestParam(value = "userId") String userId, @RequestParam(value = "postContent") String postContent){

        Post post = postService.addPost(postTypeId,userId,postContent);        ;
        if(post == null) {
            logger.warn("post : {} not added",postContent);
            return new BaseResponse("Failure", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        logger.info("post created : {}",post);
        return new BaseResponse("Success",HttpStatus.CREATED,post);
    }
	
    @GetMapping(value="/postbyuserid")
    public BaseResponse getUserPost(@RequestParam(value="userId") String userId) {
    	List<Post> posts = postService.getPostByUser(userId);
    	if(null == posts || posts.isEmpty()) {
    		logger.warn("no posts found for user : {} ", userId);
    		return new BaseResponse("No posts found or invalid userId", HttpStatus.INTERNAL_SERVER_ERROR, null);
    	}
    	else {
    		logger.info("posts retrieved");
    		return new BaseResponse("Success",HttpStatus.OK, posts);
    	}
    	
    }
    
    @GetMapping(value="/postbytype")
    public BaseResponse getPostByType(@RequestParam(value="postTypeId") int postTypeId) {
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
    public BaseResponse deletePost(@RequestParam(value = "postId")int postId, @RequestParam(value = "userId") String userId){
        
    	if(postService.findByPostId(postId) == null){
            logger.warn("Post does not exists : {}",postId);
            return new BaseResponse("Post does not exists",HttpStatus.NOT_ACCEPTABLE,false);
        }

        if(postService.deletePost(postId, userId)) {
            logger.info("Post deleted, postId: {}",postId);
            return new BaseResponse("Success", HttpStatus.OK, true);
        }

        logger.warn("Post not deleted {}",postId);
        return new BaseResponse("Failure",HttpStatus.INTERNAL_SERVER_ERROR,false);
    }
    
    @PutMapping
	public BaseResponse editPost(@RequestParam("postId")int postId, @RequestParam("postContent") String postContent) {
		if(postService.updatePost(postId, postContent)) {
			return new BaseResponse("post updated successfully",HttpStatus.OK,true);
		}
		else {
			return new BaseResponse("post could not be updated",HttpStatus.INTERNAL_SERVER_ERROR, false);
		}
	}
    
}
