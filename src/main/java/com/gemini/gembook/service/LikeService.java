package com.gemini.gembook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.gembook.model.Like;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.repository.LikeRepository;

@Service
public class LikeService {
	
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private LikeRepository likeRepository;
	
	@Autowired
	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
	
	public List<Like> getLikesByPostId(int postId) {
		List<Like> likes = null;
        try {
        	Post post = new Post(postId);
        	likes = likeRepository.findByLikeIdentityPost(post);
        	 logger.info(likes.toString());
        }catch (Exception e){
            logger.error("Exception in getLikesByPostId() : {}",e.getMessage());
        }
        return likes;
	}
	
	public Like getLike(int postId,String userId) {
		Like like = null;
        try {
//        	like = new Like(new LikeIdentity(postId,userId));
        	like = likeRepository.findById(postId,userId);
//        	 logger.info(like.toString());
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

	public boolean deleteLike(int postId,String userId) {
        try{
        	likeRepository.deleteLike(postId,userId);
        }
        catch (Exception e){
            logger.error("Exception in deleteLike() : {}",e.getMessage());
        }
        return likeRepository.findById(postId,userId) == null;
    }
	
	
}
