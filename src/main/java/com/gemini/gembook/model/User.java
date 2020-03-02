package com.gemini.gembook.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	private String userId;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@JsonIgnore
	@OneToMany(
			cascade=CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			orphanRemoval=true,
			mappedBy = "user")
	private List<Post> posts;
	
	@JsonIgnore
	@OneToMany(
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			orphanRemoval=true,
			mappedBy = "likeIdentity.user")
	private List<Like> likes;
	
	@JsonIgnore
	@OneToMany(
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			orphanRemoval=true,
			mappedBy = "user")
	private List<Comment> comments;
	
	@JsonIgnore
	@OneToMany(
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, 
			orphanRemoval=true,
			mappedBy = "commentIdentity.user")
	private List<CommentLike> commentLikes;

	public User(String firstName, String lastName, String userId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
	}
	
	public User() {}
	
	public User(String userId){
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonIgnore
	public List<Post> getPosts() {
		return posts;
	}

	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	@JsonIgnore
	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}
	
	@JsonIgnore
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	@JsonIgnore
	public List<CommentLike> getCommentLikes() {
		return commentLikes;
	}

	public void setCommentLikes(List<CommentLike> commentLikes) {
		this.commentLikes = commentLikes;
	}

	
	
}
