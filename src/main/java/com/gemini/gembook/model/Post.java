package com.gemini.gembook.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name="post" )
public class Post {
	@Id
	@Column(name="post_id")
	private int postId;
	
	@ManyToOne
	@JoinColumn(name="post_type_id")
	private PostType postType;
	
	@ManyToOne
	@JoinColumn(name="user_name")
	private User user;
	
	@Column(name="post_content")
	String postContent;
	
	@Column(name="post_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	Date postTime;
	
	@JsonIgnore
	@OneToMany(mappedBy="post", cascade = CascadeType.ALL, targetEntity=Comment.class)
	Set<Comment> comments = new HashSet<>();

	public Post() {
	}
	
	public Post(PostType postType, User user, String postContent) {
		this.postType = postType;
		this.user = user;
		this.postContent = postContent;
		postTime = new Date(System.currentTimeMillis());
	}
	
	public Integer getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public PostType getPostType() {
		return postType;
	}

	public void setPostType(PostType postType) {
		this.postType = postType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User userName) {
		this.user = userName;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	
	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
}
