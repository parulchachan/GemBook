package com.gemini.gembook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gemini.gembook.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	@Query(
			value="select * from post where user_name = ?1", nativeQuery = true
			)
	public List<Post> getPostByUserName(String userName);
	
	@Query(
			value="select * from post where post_type_id = ?1", nativeQuery = true
			)
	public List<Post> getPostByType(int postTypeId);
	
	@Modifying
	@Transactional
	@Query(
			value="delete from post where post_id = ?1", nativeQuery = true
			)
	public void deletePostById(int postId);
	
	@Query(
			value="select * from post where post_id = ?1", nativeQuery = true
			)
	public Post getPostById(int postId);
	
	@Modifying
	@Transactional
	@Query(
			value="update post set post_content = ?2 where post_id = ?1",
			nativeQuery = true )
	public  int updatePost(int postId, String postContent);
}
