package com.gemini.gembook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gemini.gembook.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{
	
	@Modifying
	@Transactional
	@Query(
			value = "Delete from comments\n" +
					"where comment_id = ?1",
           nativeQuery = true
	)	
	void deleteComment(int commentId);

	
	@Modifying
	@Transactional
	@Query(
			value="update comments set comment_content = ?2 where comment_id = ?1",
			nativeQuery = true )
	public  int updateComment(int commentId, String commentContent);
	
	
	@Query(
			value="select * from (select * from comments where post_id = ?1 order by comment_time desc limit 5) com order by com.comment_time asc", nativeQuery = true)
	public List<Comment> getLatestComments(int postId);
	
	
	@Query(
			value="select * from comments where post_id = ?1", nativeQuery = true )
	public List<Comment> getAllComments(int postId);
	
	
	@Query(
            value = "Select * from comments where comment_id = ?1",
            nativeQuery = true
    )
    Comment findByCommentId(int commentId);
	
}
