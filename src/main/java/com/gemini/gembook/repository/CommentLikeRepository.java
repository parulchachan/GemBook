package com.gemini.gembook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gemini.gembook.model.CommentIdentity;
import com.gemini.gembook.model.CommentLike;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentIdentity>{
	
	@Query(
            value = "select * from comment_likes\n"+
            		"where comment_id =?1 and user_id =?2",
            nativeQuery = true
    )
	CommentLike findById(int commentId, String userId);
	
	@Modifying
	@Transactional
	@Query(
			value = "update comment_likes\n" +
					"set comment_like_flag = ?3," +
					"comment_like_time = ?4\n " +
					"where comment_id = ?1\n" +
					"and user_id = ?2",
           nativeQuery = true
	)
	int updateCommentLike(int commentId, String userId, String likeFlag, long likeTime);

}
