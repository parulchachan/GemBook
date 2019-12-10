package com.gemini.gembook.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gemini.gembook.model.Comment;
import com.gemini.gembook.repository.CommentRepository;

@Service
public class CommentService {
	
	private final Logger logger = LoggerFactory.getLogger(PostService.class);
	private CommentRepository commentRepository;
	
	@Autowired
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	public  List<Comment> getCommentsByPostId(int postId){
		List<Comment> comments = null;
        try {
        	comments = commentRepository.findByPostId(postId);
        }catch (Exception e){
            logger.error("Exception in getCommentsByPostId() : {}",e.getMessage());
        }
        return comments;
	}
	
	public Comment addComment(Comment commentModel) { 
		Comment comment = null;
		try {
			comment = commentRepository.save(commentModel);
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

	public Comment findByCommentId(int commentId) {
		Comment comment = new Comment();
        try{
        	comment = commentRepository.findByCommentId(commentId);
        }
        catch(Exception e){
            logger.error("Exception in findByCommentId() : {}",e.getMessage());
        }
        return  comment;
	}
	
}
