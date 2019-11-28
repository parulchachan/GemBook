package com.gemini.gembook.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name="post" )
public class Post {
	@Id
	@JsonIgnore
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer postId = 1;
	
	@ManyToOne
	@JoinColumn(name="post_type_id")
	PostType postType;
	
	@ManyToOne
	@JoinColumn(name="user_name")
	User user;
	
	@Column(name="post_content")
	String postContent;
	
	@Column(name="post_time")
	Date postTime;
	
	public Post() {
	}
	
	public Post(PostType postType, User user, String postContent) {
		this.postType = postType;
		this.user = user;
		this.postContent = postContent;
		postTime = new Date(System.currentTimeMillis());
	}
	
	@JsonIgnore
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

	public User getUserId() {
		return user;
	}

	public void setUserId(User userName) {
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
	
	
}
