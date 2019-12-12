package com.gemini.gembook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.gemini.gembook.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer>{

	@Query(
            value = "select * from posts\n"+
            		"order by post_time desc limit ?1 , 2;",
            nativeQuery = true
    )
	List<Post> getnextfifteenPost(int postCount);
	
	@Modifying
	@Transactional
	@Query(
			value = "Delete from posts\n" +
					"where post_id = ?1\n" +
					"and user_id = ?2",
           nativeQuery = true
	)	
	void deletePost(int postId, String userId);
	
	@Query(
            value = "Select * from posts where post_id = ?1",
            nativeQuery = true
    )
    Post findByPostId(int postId);
	
	@Query(
            value = "Select * from posts where post_id = ?1",
            nativeQuery = true
    )
    Post findCompletePost(int postId);
}
