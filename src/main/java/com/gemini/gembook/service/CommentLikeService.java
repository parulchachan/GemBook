package com.gemini.gembook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gemini.gembook.model.CommentLike;
import com.gemini.gembook.repository.CommentLikeRepository;
import com.gemini.gembook.repository.CommentRepository;
import com.gemini.gembook.repository.UsersRepository;

@Service
public class CommentLikeService {
	
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private UsersRepository userRepository;
	private CommentRepository commentReository;
	private	CommentLikeRepository commentLikeRepository;
	private final int ZERO = 0;
	private final int ONE = 1;
	
	@Autowired
	public CommentLikeService(UsersRepository userRepository, 
			CommentLikeRepository commentLikeRepository, CommentRepository commentReository) {
		this.userRepository = userRepository;
		this.commentReository = commentReository;
		this.commentLikeRepository = commentLikeRepository;
	}
	
	public boolean isCommentIdentityValid(int commentId,String userId){
		if(commentReository.existsById(commentId)) {
			if(userRepository.existsById(userId))
				return true;
		}
		return false;
	}
	
	public CommentLike getCommentLike(int commentId,String userId) {
		CommentLike commentLike = null;
        try {
        	commentLike = commentLikeRepository.findById(commentId,userId);
        }catch (Exception e){
            logger.error("Exception in getLike calling findById() : {}",e.getMessage());
        }
        return commentLike;
	}
	
	public CommentLike saveCommentLike(CommentLike commentLike) {
		CommentLike commentLike1 = null;
		
		try {
			commentLike1 = commentLikeRepository.save(commentLike);
        }
        catch (Exception e){
            logger.error("Exception in saveLike() : {}",e.getMessage());
        }
		return commentLike1;
	}
	
	public boolean updateCommentLike(int commentId, String userId, String likeFlag, long likeTime) {
		int status = ZERO;
		try {
			status = commentLikeRepository.updateCommentLike(commentId,userId,likeFlag,likeTime);
        }
        catch (Exception e){
            logger.error("Exception in updateLike() : {}",e.getMessage());
        }
		return (ONE == status) ? true : false;
	}
	
	
}
