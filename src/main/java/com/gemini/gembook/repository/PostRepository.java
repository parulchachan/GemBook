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
			value="select * from post where user_id = ?1", nativeQuery = true
			)
	public List<Post> getPostByUserName(String userId);
	
	
	@Query(
			value="select * from post where post_type_id = ?1", nativeQuery = true
			)
	public List<Post> getPostByType(int postTypeId);

	
	@Query(
            value = "select * from post\n"+
            		"order by post_time desc limit ?1 , 2;",
            nativeQuery = true
    )
	List<Post> getnextfifteenPost(int postCount);
	
	
	@Modifying
	@Transactional
	@Query(
			value="update post set post_content = ?2 where post_id = ?1",
			nativeQuery = true )
	public  int updatePost(int postId, String postContent);


	@Modifying
	@Transactional
	@Query(	
			value = "Delete from post\n" +
					"where post_id = ?1\n" +
					"and user_id = ?2",
           nativeQuery = true
	)	
	void deletePost(int postId, String userId);
	
	
	@Query(
            value = "Select * from post where post_id = ?1",
            nativeQuery = true
    )
    Post findByPostId(int postId);
	
	@Query(
            value = "Select * from posts where post_id = ?1",
            nativeQuery = true
    )
    Post findCompletePost(int postId);
}
