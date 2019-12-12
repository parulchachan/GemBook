package com.gemini.gembook.repository;

import com.gemini.gembook.model.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostSortingRepository extends PagingAndSortingRepository<Comment, Integer> {
	
}
