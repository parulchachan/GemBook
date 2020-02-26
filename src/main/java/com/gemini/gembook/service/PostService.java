package com.gemini.gembook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.gemini.gembook.model.Photo;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.model.User;
import com.gemini.gembook.repository.PhotoRepository;
import com.gemini.gembook.repository.PostRepository;
import com.gemini.gembook.repository.UsersRepository;

@Service
public class PostService {
	
	private PostRepository postRepository;
	private UsersRepository userRepository;
	private PhotoRepository photoRepository;
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private final int ZERO = 0;
	private final int ONE = 1;
	
	@Autowired
	public PostService(PostRepository postRepository, UsersRepository userRepository, PhotoRepository photoRepository ){
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.photoRepository = photoRepository;
	}
	
	public List<Post> getAllPosts() {
		List<Post> posts = null;
		try {
			posts = postRepository.getRecentPosts();
		}
		catch(Exception e) {
			logger.error("postRepository.getRecentPosts() throws an exception: {}",e.getMessage());
		}
		return posts; 
	}
	
	public Post findByPostId(int postId){

		Post post = null;
        try{
        	post = postRepository.findByPostId(postId);
        }
        catch(Exception e){
            logger.error("Exception in findByPostId() : {}",e.getMessage());
        }
        return  post;
    }
	
	public List<Post> getPostByUser(String userId) {
		List<Post> posts = null;
		try {
			User user = null;
			user = this.userRepository.findByUserName(userId);
			if(user == null) return posts;
			posts = postRepository.getPostByUserName(userId);
		}
		catch(Exception e) {
			logger.error("postRepository.getPostByUserName() throws an exception: {}",e.getMessage());
		}
		return posts;
	}
	
	public List<Post> getPostByPostType(int postTypeId) {
		List<Post> posts = null;
		try {
			posts = postRepository.getPostByType(postTypeId);
		}
		catch(Exception e) {
			logger.error("postRepository.getPostByType() throws an exception: {}",e.getMessage());
		}
		return posts;
	}

	public List<Post> getNextPost(long postTime) {
		List<Post> posts = null;
		
		try {
			if(ZERO == postTime) {
				posts = postRepository.getRecentPosts();
			}
			else {
				posts = postRepository.getNextPosts(postTime);
			}
        }
        catch (Exception e){
            logger.error("Exception in getNextPost() : {}",e.getMessage());
        }
		return posts;
	}
	
	public Post addPost(int postType, String userId, String postContent, MultipartFile files) {
		Post post = null;
		try {
			post = postRepository.save(new Post(postType,userId,postContent));
			if(files != null) {
				Photo photo = new Photo(post.getPostId(),files);
				this.photoRepository.save(photo);
			}
        }
        catch (Exception e){
            logger.error("Exception in addPost() : {}",e.getMessage());
        }
		return post;
	}
	
	public boolean updatePost(int postId, String postContent, MultipartFile files) {
		int status = ZERO;
		try {
			status = postRepository.updatePost(postId, postContent);
			this.photoRepository.deletePhotos(postId);
			if(files != null) {
				Photo photo = new Photo(postId,files);
				this.photoRepository.save(photo);
			}
		}
		catch(Exception e) {
			logger.error("postRepository.updatePost throws an exception, {} ", e.getMessage());
		}
		return (ONE == status) ? true : false;
	}
	
	public boolean deletePost(int postId,String userId) {
        int status = ZERO;
        try {
        	Post post = postRepository.findByPostId(postId);
        	if(userId.equals(post.getUser().getUserId())){
                postRepository.delete(post);
        		status= ONE;
        	}	
        	else {
        		logger.info("user not matched");
        	}
        }
        catch(Exception e) {
        	logger.error("Exception in deletePost() : {}",e.getMessage());
        }
        return (ONE == status) ? true : false;
    }
	
}
