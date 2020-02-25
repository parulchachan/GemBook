package com.gemini.gembook.model;

import java.util.List;


public class CompletePost {

	private Post post;
	
	private List<Like> likes;
	
	private List<Comment> comments;
	
	public CompletePost(Post post,List<Like> likes,List<Comment> comments ) {
		this.post = post;
		this.likes = likes;
		this.comments = comments;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Comment> getComment() {
		return comments;
	}

	public void setComment(List<Comment> comments) {
		this.comments = comments;
	}
	
}
