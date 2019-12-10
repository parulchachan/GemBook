package com.gemini.gembook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gemini.gembook.model.Like;
import com.gemini.gembook.model.LikeIdentity;
import com.gemini.gembook.model.Post;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeIdentity>{
	
	
	@Query(
            value = "select * from likes\n"+
            		"where post_id = ?1",
            nativeQuery = true
    )
	List<Like> findByLikeIdentityPost(Post post);
	
	
	
	@Query(
            value = "select * from likes\n"+
            		"where post_id =?1 and user_id =?2",
            nativeQuery = true
    )
	Like findById(int postId, String userId);

	@Modifying
	@Transactional
	@Query(
			value = "Delete from likes\n" +
					"where post_id = ?1\n" +
					"and user_id = ?2",
           nativeQuery = true
	)
	void deleteLike(int postId, String userId);

}
