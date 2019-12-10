package com.gemini.gembook.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.repository.PostRepository;

@Service
public class PostService {
	
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private PostRepository postRepository;
	
	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public List<Post> getPosts() {
		List<Post> posts = null;
        try {
        	posts = postRepository.findAll();
        }catch (Exception e){
            logger.error("Exception in findAll() : {}",e.getMessage());
        }
        return posts;
	}
	
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
	
	public Post addPost(Post postModel) {
		Post post = null;
		
		try {
			post = postRepository.save(postModel);
        }
        catch (Exception e){
            logger.error("Exception in addPost() : {}",e.getMessage());
        }
		return post;
	}
	
	public Post findByPostId(int postId){
		Post post = new Post();
        try{
        	post = postRepository.findByPostId(postId);
        }
        catch(Exception e){
            logger.error("Exception in findByPostId() : {}",e.getMessage());
        }
        return  post;
    }
	
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
