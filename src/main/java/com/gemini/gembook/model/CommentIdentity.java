package com.gemini.gembook.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Embeddable
@Table( name="comment_likes")
public class CommentIdentity implements Serializable {

	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@NotNull
	@ManyToOne
	@JoinColumn(name="comment_id")
	private Comment comment;
	
	public CommentIdentity() {}
	
	public CommentIdentity(int commentId, String userId) {
		this.comment = new Comment(commentId) ;
		this.user =new User(userId);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	@JsonIgnore
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}
