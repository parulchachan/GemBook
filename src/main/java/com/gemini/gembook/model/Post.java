package com.gemini.gembook.model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="post_content")
	private String postContent;
	
	@Column(name="post_time")
//	@Temporal(TemporalType.TIMESTAMP)
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private long postTime;
	
	public Post() {}
	
	public Post(int postId) {
		this.postId = postId;
	}
	
	public Post(int postType, String userId, String postContent) {
		this.postType = new PostType(postType) ;
		this.user = new User(userId);
		this.postContent = postContent;
		Date date=new Date();
		this.postTime = date.getTime();
//		this.postTime = Instant.now().getEpochSecond();
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
	
	public long getPostTime() {
		return postTime;
	}

	public void setPostTime(long postTime) {
		this.postTime = postTime;
	}

}
