package com.gemini.gembook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.model.PostType;
import com.gemini.gembook.model.User;
import com.gemini.gembook.repository.PostRepository;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private final int ZERO = 0;
	private final int ONE = 1;
	
	@Autowired
	public PostService(PostRepository postRepository, UsersService usersService, PostTypeService postTypeService) {
		this.postRepository = postRepository;
	}
	
	public List<Post> getAllPosts() {
		List<Post> posts = null;
		try {
			posts = postRepository.findAll();
		}
		catch(Exception e) {
			logger.error("postRepository.findAll() throws an exception: {}",e.getMessage());
		}
		return posts; 
	}
	
	public List<Post> getPostByUser(String userId) {
		List<Post> posts = null;
		try {
			posts = postRepository.getPostByUserName(userId);
		}
		catch(Exception e) {
			logger.error("postRepository.getPostByUserName() throws an exception: {}",e.getMessage());
		}
		return posts;
	}
	
	public List<Post> getPostByType(int postTypeId) {
		List<Post> posts = null;
		try {
			posts = postRepository.getPostByType(postTypeId);
		}
		catch(Exception e) {
			logger.error("postRepository.getPostByType() throws an exception: {}",e.getMessage());
		}
		return posts;
	}
	
//	public Post getPostById(int postId) {
//		Post post = null;
//		try {
//			post = postRepository.getPostById(postId);
//		}
//		catch(Exception e) {
//			logger.error("postRepository.getPostById throws an exception, {}",e.getMessage());
//		}
//		return post;
//	}
	
	public boolean updatePost(int postId, String postContent) {
		int status = ZERO;
		try {
			status = postRepository.updatePost(postId, postContent);
		}
		catch(Exception e) {
			logger.error("postRepository.updatePost throws an exception, {} ", e.getMessage());
		}
		return (ONE == status) ? true : false;
	}
	
//	public List<Post> getPosts() {
//		List<Post> posts = null;
//        try {
//        	posts = postRepository.findAll();
//        }catch (Exception e){
//            logger.error("Exception in findAll() : {}",e.getMessage());
//        }
//        return posts;
//	}
	
	public List<Post> getnextfifteenPost(int postCount) {
		List<Post> posts = null;
		
		try {
			posts = postRepository.getnextfifteenPost(postCount);
        }
        catch (Exception e){
            logger.error("Exception in getnextfifteenPost() : {}",e.getMessage());
        }
		return posts;
	}

	public Post addPost(int postType, String userId, String postContent) {
		Post post = null;
		try {
			post = postRepository.save(new Post(postType,userId,postContent));
        }
        catch (Exception e){
            logger.error("Exception in addPost() : {}",e.getMessage());
        }
		return post;
	}
	
//	public Post findByPostId(int postId){
//		Post post = new Post();
//        try{
//        	post = postRepository.findByPostId(postId);
//        }
//        catch(Exception e){
//            logger.error("Exception in findByPostId() : {}",e.getMessage());
//        }
//        return  post;
//    }
	
	public boolean deletePost(int postId,String userId) {
        try{
        	postRepository.deletePost(postId,userId);
        }
        catch (Exception e){
            logger.error("Exception in deletePost() : {}",e.getMessage());
        }
        return postRepository.findByPostId(postId) == null;
    }
}
