package com.gemini.gembook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gemini.gembook.model.PostType;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType, Integer> {
	
	@Query(
		value="select * from post_type where post_type_id = ?1", nativeQuery = true
	)
	public PostType findByPostTypeId(int id);
}
