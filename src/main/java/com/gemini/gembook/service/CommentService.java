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
	
	private Logger logger = LoggerFactory.getLogger(CommentService.class);
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
	
	public Comment insertComment(int postId, String userName, String commentContent) {
		Post post = postService.getPostById(postId);
		if(null == post) {
			logger.error("post not found with post id; {}",postId);
			return null;
		}
		User user = usersService.findByUserName(userName);
		if(null == user) {
			logger.error("user not found with user id: {}",userName);
			return null;
		}
		return commentRepository.save(new Comment(post, user, commentContent));
	}
	
	public boolean deleteComment(int commentId) {
		int status = ZERO;
		try {
			status = commentRepository.deleteComment(commentId);
		}
		catch(Exception e) {
			logger.error("commentRepository.deleteComment throws an exception, {}", e.getMessage());
		}
		return (ONE == status) ? true : false;		
	}
	
	public Comment getCommentById(int commentId) {
		Comment comment = null;
		try {
			comment = commentRepository.getCommentById(commentId);
		}
		catch(Exception e) {
			logger.error("commentRepository.getCommentById throws an exception, {}", e.getMessage());
		}
		return comment;
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
	
}
