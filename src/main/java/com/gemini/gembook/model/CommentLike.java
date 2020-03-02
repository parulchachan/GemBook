package com.gemini.gembook.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name="comment_likes")
public class CommentLike {
	
	@EmbeddedId
    private CommentIdentity commentIdentity;
	
	@Column(name="comment_like_time")
	private long commentLikeTime;
	
	@Column(name="comment_like_flag")
	private String commentLikeFlag;
	
	public CommentLike() {
	}
	
	public CommentLike(CommentIdentity commentIdentity,String commentLikeFlag) {
		this.commentIdentity = commentIdentity;
		Date date=new Date();
		this.commentLikeTime = date.getTime();
		this.commentLikeFlag = commentLikeFlag;
	}

	public CommentIdentity getCommentIdentity() {
		return commentIdentity;
	}

	public void setCommentIdentity(CommentIdentity commentIdentity) {
		this.commentIdentity = commentIdentity;
	}

	public long getCommentLikeTime() {
		return commentLikeTime;
	}

	public void setCommentLikeTime(long commentLikeTime) {
		this.commentLikeTime = commentLikeTime;
	}

	public String getCommentLikeFlag() {
		return commentLikeFlag;
	}

	public void setCommentLikeFlag(String commentLikeFlag) {
		this.commentLikeFlag = commentLikeFlag;
	}
	
	
}
