package com.gemini.gembook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gemini.gembook.model.Post;
import com.gemini.gembook.repository.PostRepository;

@Service
public class PostService {
	
	private PostRepository postRepository;
	
	@Autowired
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Iterable<Post> getPosts() {
		return postRepository.findAll();
	}
	
	public Post setPost(Post post) {
		return postRepository.save(post);
	}
}
