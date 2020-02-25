package com.gemini.gembook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gemini.gembook.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {

}
