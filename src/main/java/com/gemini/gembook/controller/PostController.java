package com.gemini.gembook.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.gemini.gembook.model.CompletePost;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.service.PostService;

@RestController
@RequestMapping(value="/dashboard/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	

//    @GetMapping(value="allposts")
//    public BaseResponse getAllPosts(){
//        List<Post> posts =  postService.getAllPosts();
//
//        if(posts == null) {
//        	logger.warn("post not found.");
//            return new BaseResponse("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR,null);
//        }
//        
//        if(posts.isEmpty()) {
//        	logger.info("post not found.");
//            return new BaseResponse("No Post Found", HttpStatus.NOT_FOUND,null);        
//        }
//
//        logger.info("Posts retrieved");
//        return new BaseResponse("Success",HttpStatus.OK, posts);
//    }
    

    @GetMapping(value="/latest")
    public BaseResponse getCompletePost(HttpServletResponse response){
        List<CompletePost> completePost = postService.getCompletePost();

        if(completePost == null) {
        	logger.warn("Database error");
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new BaseResponse("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
        
        if(completePost.isEmpty()) {
        	logger.info("post not found.");
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new BaseResponse("No Post Found", HttpStatus.NOT_FOUND,null);        
        }

        logger.info("Posts retrieved");
        response.setStatus(HttpServletResponse.SC_OK);
        return new BaseResponse("Success",HttpStatus.OK, completePost);
    }
    
    @GetMapping(value="/nextposts")
    public BaseResponse getNextPosts(@RequestParam(value = "postTime") long postTime,
    		HttpServletResponse response){ 
        List<CompletePost> completePost = postService.getNextCompletePost(postTime);

        if(completePost == null) {
        	logger.warn("Database error");
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new BaseResponse("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR,null);
        }
        
        if(completePost.isEmpty()) {
        	logger.info("post not found.");
        	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new BaseResponse("No Post Found", HttpStatus.NOT_FOUND,null);        
        }

        logger.info("Posts retrieved");
        response.setStatus(HttpServletResponse.SC_OK);
        return new BaseResponse("Success",HttpStatus.OK, completePost);
    }

    @GetMapping(value="/postbytype")
    public BaseResponse getPostByType(@RequestParam(value="postTypeId") int postTypeId,
    		HttpServletResponse response) {
    	List<CompletePost> completePost = postService.getCompletePostByType(postTypeId);
    	if(null == completePost) {
    		logger.warn("no posts found with type : {} ", postTypeId);
    		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    		return new BaseResponse("Server Error", HttpStatus.INTERNAL_SERVER_ERROR, null);
    	}if(completePost.isEmpty()) {
    		response.setStatus(HttpServletResponse.SC_NO_CONTENT);//204 
    		return new BaseResponse("No posts found", HttpStatus.OK, null);
    	}
    	else {
    		logger.info("posts retrieved");
    		response.setStatus(HttpServletResponse.SC_OK);
    		return new BaseResponse("Success",HttpStatus.OK, completePost);
    	}

    }
	
    @GetMapping(value="/postbyuserid")
    public BaseResponse getUserPost(@RequestParam(value="userId") String userId,
    		HttpServletResponse response) {
    	List<Post> posts = postService.getPostByUser(userId);
    	if(null == posts) {
    		logger.warn("Wrong UserId", userId);
    		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    		return new BaseResponse("invalid userId", HttpStatus.BAD_REQUEST, null);
    	}else
    	if(posts.isEmpty()) {
    		logger.warn("no posts found for user : {} ", userId);
    		response.setStatus(HttpServletResponse.SC_NO_CONTENT);//204 
    		return new BaseResponse("No posts found", HttpStatus.OK, null);
    	}
    	else {
    		logger.info("posts retrieved");
    		response.setStatus(HttpServletResponse.SC_OK);
    		return new BaseResponse("Success",HttpStatus.OK, posts);
    	}
    }
	
//  @GetMapping(value="/nextpage")
//  public BaseResponse getNextPostPage(@RequestParam(value = "pageNo") int pageNo){ 
//      List<Post> posts = postService.getNextPostPage(PageRequest.of(pageNo, 3));
//      
//      if(posts == null) {
//      	logger.info("post not found.");
//          return new BaseResponse("Internal Error", HttpStatus.NOT_FOUND,null);
//      }
//
//      logger.info("Posts retrieved");
//      return new BaseResponse("Success",HttpStatus.OK, posts);
//  }
    
    @PostMapping
    public BaseResponse addPost(@RequestParam(value = "postTypeId") int postTypeId,
    		@RequestParam(value = "userId") String userId, 
    		@RequestParam(value = "postContent") String postContent, 
    		HttpServletResponse response){

        Post post = postService.addPost(postTypeId,userId,postContent);        ;
        if(post == null) {
            logger.warn("post : {} not added",postContent);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new BaseResponse("UserId or PostTypeId is wrong", HttpStatus.BAD_REQUEST,null);
        }
        logger.info("post created : {}",post);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return new BaseResponse("Post Created Successfully",HttpStatus.CREATED,post);
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
    
    @DeleteMapping
    public BaseResponse deletePost(@RequestParam(value = "postId")int postId, 
    		@RequestParam(value = "userId") String userId){
        
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
       
}
