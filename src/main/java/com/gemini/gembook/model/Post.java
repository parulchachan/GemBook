package com.gemini.gembook.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name="posts" )
public class Post {
	
	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	
	@ManyToOne
	@JoinColumn(name="post_type_id")
	private PostType postType;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="post_content")
	private String postContent;
	
	@Column(name="post_time")
	private Date postTime;
	
	public Post() {}
	
	public Post(int postId) {
		this.postId = postId;
	}
	
	public Post(int postType, String userId, String postContent) {
		this.postType = new PostType(postType) ;
		this.user =new User(userId);
		this.postContent = postContent;
		this.postTime = new Date();
	}
	
	public Integer getPostId() {
		return postId;
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
