package com.gemini.gembook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gemini.gembook.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
	@Modifying
	@Transactional
	@Query(	
			value = "Delete from photos\n" +
					"where post_id = ?1",
           nativeQuery = true
	)	
	int deletePhotos(int postId);
}
