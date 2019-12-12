package com.gemini.gembook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.gemini.gembook.model.Comment;
import com.gemini.gembook.model.Post;
import com.gemini.gembook.model.User;
import com.gemini.gembook.repository.CommentRepository;


@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UsersService usersService;
	
	private final Logger logger = LoggerFactory.getLogger(CommentService.class);
	private static final int ZERO = 0;
	private static final int ONE = 1;
	
	public CommentService() {
	}

	public List<Comment> getAllComments(int postId) {
		List<Comment> comments = null;
		try {
			comments = commentRepository.getAllComments(postId);
		}
		catch(Exception e) {
			logger.error("commentRepository.findAll throws an exception, {}",e.getMessage());
		}
		return comments;
	}
	
	public Comment addComment(int postId, String userId, String commentContent) { 
		Comment comment = null;
		try {
			comment = commentRepository.save(new Comment(postId, userId,commentContent));
        }
        catch (Exception e){
            logger.error("Exception in addComment() : {}",e.getMessage());
        }
		return comment;
	}
	
	public boolean deleteComment(int commentId) {
        try{
        	commentRepository.deleteComment(commentId);
        }
        catch (Exception e){
            logger.error("Exception in deleteComment() : {}",e.getMessage());
        }
        return commentRepository.findByCommentId(commentId) == null;
    }
	
	public boolean updateComment(int commentId, String commentContent) {
		int status = ZERO;
		try {
			status = commentRepository.updateComment(commentId, commentContent);
		}
		catch(Exception e) {
			logger.error("commentRepository.updateComment throws an exception, {} ", e.getMessage());
		}
		return (ONE == status) ? true : false;
	}
	
	public List<Comment> getLatestComments(int postId) {
		List<Comment> comments = null;
		try {
			comments = commentRepository.getLatestComments(postId);
		}
		catch(Exception e) {
			logger.error("commentRepository.getLatestComments throws an exception, {}",e.getMessage());
		}
		return comments;
	}
	
//	public  List<Comment> getCommentsByPostId(int postId){
//		List<Comment> comments = null;
//        try {
//        	comments = commentRepository.findByPostId(postId);
//        }catch (Exception e){
//            logger.error("Exception in getCommentsByPostId() : {}",e.getMessage());
//        }
//        return comments;
//	}
//	
	
//	public Comment findByCommentId(int commentId) {
//		Comment comment = new Comment();
//        try{
//        	comment = commentRepository.findByCommentId(commentId);
//        }
//        catch(Exception e){
//            logger.error("Exception in findByCommentId() : {}",e.getMessage());
//        }
//        return  comment;
//	}
	
}
