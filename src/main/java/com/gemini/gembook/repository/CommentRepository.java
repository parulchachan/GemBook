package com.gemini.gembook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gemini.gembook.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
	
	@Modifying
	@Transactional
	@Query(
			value = "Delete from comments\n" +
					"where comment_id = ?1",
           nativeQuery = true
	)	
	void deleteComment(int commentId);
	
	
	@Query(
            value = "Select * from comments where post_id = ?1",
            nativeQuery = true
    )
    List<Comment> findByPostId(int postId);
	
	@Query(
            value = "Select * from comments where comment_id = ?1",
            nativeQuery = true
    )
    Comment findByCommentId(int commentId);
	
	
}
