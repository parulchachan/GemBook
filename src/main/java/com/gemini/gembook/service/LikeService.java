package com.gemini.gembook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.gembook.model.Like;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.repository.LikeRepository;
import com.gemini.gembook.repository.PostRepository;
import com.gemini.gembook.repository.UsersRepository;

@Service
public class LikeService {
	
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private LikeRepository likeRepository;
	private UsersRepository userRepository;
	private PostRepository postRepository;
	private final int ZERO = 0;
	private final int ONE = 1;
	
	@Autowired
	public LikeService(LikeRepository likeRepository,PostRepository postRepository,
			UsersRepository userRepository) {
		this.likeRepository = likeRepository;
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	public List<Like> getLikesByPostId(int postId) {
		List<Like> likes = null;
        try {
        	Post post = new Post(postId);
        	likes = likeRepository.findByLikeIdentityPost(post);
        }catch (Exception e){
            logger.error("Exception in getLikesByPostId() : {}",e.getMessage());
        }
        return likes;
	}
	
	public boolean isLikeIdentityValid(int postId,String userId) {
		if(postRepository.existsById(postId)) {
			if(userRepository.existsById(userId))
				return true;
		}
		return false;
	}
	
	public Like getLike(int postId,String userId) {
		Like like = null;
        try {
        	like = likeRepository.findById(postId,userId);
        }catch (Exception e){
            logger.error("Exception in getLike calling findById() : {}",e.getMessage());
        }
        return like;
	}

	public Like saveLike(Like like) {
		Like like1 = null;
		
		try {
			like1 = likeRepository.save(like);
        }
        catch (Exception e){
            logger.error("Exception in saveLike() : {}",e.getMessage());
        }
		return like1;
	}
	
	public boolean updateLike(int postId,String userId,String likeFlag,long likeTime) {
		int status = ZERO;
		try {
			status = likeRepository.updateLike(postId,userId,likeFlag,likeTime);
        }
        catch (Exception e){
            logger.error("Exception in updateLike() : {}",e.getMessage());
        }
		return (ONE == status) ? true : false;
	}
	
}
