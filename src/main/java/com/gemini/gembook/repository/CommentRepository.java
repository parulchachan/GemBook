package com.gemini.gembook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gemini.gembook.model.Comment;
import com.gemini.gembook.model.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer>{

	@Query(
			value="select * from comment where comment_id = ?1",
			nativeQuery = true )
	public Comment getCommentById(int commentId);
	
	@Modifying
	@Transactional
	@Query(
			value="delete from comment where comment_id = ?1", 
			nativeQuery = true )
	public int deleteComment(int commentId);
	
	@Modifying
	@Transactional
	@Query(
			value="update comment set comment_content = ?2 where comment_id = ?1",
			nativeQuery = true )
	public  int updateComment(int commentId, String commentContent);
	
	@Query(
			value="select * from (select * from comment where post_id = ?1 order by comment_time desc limit 5) com order by com.comment_time asc", nativeQuery = true)
	public List<Comment> getLatestComments(int postId);
	
	@Query(
			value="select * from comment where post_id = ?1", nativeQuery = true )
	public List<Comment> getAllComments(int postId);
	
}
