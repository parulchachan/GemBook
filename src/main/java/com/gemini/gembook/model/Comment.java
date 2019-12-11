package com.gemini.gembook.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="Comment")
public class Comment {
	@Id
	@Column(name="comment_id")
	private int commentId;
	
	@ManyToOne
	@JoinColumn(name="post_id")
	Post post;
	
	@ManyToOne
	@JoinColumn(name="user_Name")
	User commenter;
	
	@Column(name="comment_content")
	private String commentContent;
	
	@Column(name="comment_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date commentTime;
	
	public Comment() {
	}
	
	public Comment(Post post, User user, String commentContent) {
		this.post = post;
		this.commenter = user;
		this.commentContent = commentContent;
		this.commentTime = new Date(System.currentTimeMillis());
	}
	
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getCommenter() {
		return commenter;
	}

	public void setCommenter(User commenter) {
		this.commenter = commenter;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getDateTime() {
		return commentTime;
	}

	public void setDateTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	
}
