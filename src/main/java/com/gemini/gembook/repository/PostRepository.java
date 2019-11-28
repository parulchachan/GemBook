package com.gemini.gembook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemini.gembook.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}
