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
	
	private PostTypeService postTypeService;
	
	private UsersService usersService;
	
	private Logger logger = LoggerFactory.getLogger(PostService.class);
	private static final int ZERO = 0;
	private static final int ONE = 1;
	
	@Autowired
	public PostService(PostRepository postRepository, UsersService usersService, PostTypeService postTypeService) {
		this.postRepository = postRepository;
		this.usersService = usersService;
		this.postTypeService = postTypeService;
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
	
	public Post addPost(int postTypeId, String userName, String postContent) {
		User user = usersService.findByUserName(userName);
		if(null == user) {
			logger.error("userName {} not found",userName);
			return null;
		}
    	PostType postType = postTypeService.getPostTypeById(postTypeId);
    	if(null == postType) {
    		logger.error("postTypeId {} not found",postTypeId);
    		return null;
    	}
        return postRepository.save(new Post(postType,user,postContent));
	}
	
	public List<Post> getPostByUser(String userName) {
		List<Post> posts = null;
		try {
			posts = postRepository.getPostByUserName(userName);
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
	
	public boolean deletePost(int postId) {
		try {
			postRepository.deletePostById(postId);
		}
		catch(Exception e) {
			logger.error("postRepository.deletePostByUser throws an exception: {}",e.getMessage());
		}
		Post post = getPostById(postId);
		return null == post;
	}
	
	public Post getPostById(int postId) {
		Post post = null;
		try {
			post = postRepository.getPostById(postId);
		}
		catch(Exception e) {
			logger.error("postRepository.getPostById throws an exception, {}",e.getMessage());
		}
		return post;
	}
	
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
}
