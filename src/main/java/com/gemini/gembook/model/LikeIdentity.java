package com.gemini.gembook.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@Entity
@Embeddable
@Table( name="likes")

public class LikeIdentity implements Serializable  {
	@NotNull
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;
	
	public LikeIdentity() {}
	
	public LikeIdentity(int postId, String userId) {
		this.post = new Post(postId) ;
		this.user =new User(userId);
	}

	public User getUser() {
		return user;
	}

	public void setUserId(User user) {
		this.user = user;
	}
	
	@JsonIgnore
	public Post getPost() {
		return post;
	}

	public void setPostId(Post post) {
		this.post = post;
	}
	
//	@Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        LikeIdentity that = (LikeIdentity) o;
//
//        if (!user.equals(that.user)) return false;
//        return post.equals(that.post);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = user.hashCode();
//        result = 31 * result + post.hashCode();
//        return result;
//    }
	
}
