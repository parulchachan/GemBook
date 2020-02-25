package com.gemini.gembook.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name="post" )
public class Post {
	
	@Id
	@Column(name="post_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int postId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name="post_type_id")
	private PostType postType;
	
	@Column(name="post_content")
	private String postContent;
	
	@Column(name="post_time")
	private long postTime;
	
	@OneToMany(
			cascade = CascadeType.ALL, 
			orphanRemoval=true,
			mappedBy = "likeIdentity.post")
	private List<Like> likes;

	@OneToMany(
			cascade = CascadeType.ALL,
			orphanRemoval=true,
			mappedBy = "post")
	private List<Comment> comments;
	
	@OneToMany(
			cascade = CascadeType.ALL,
			orphanRemoval=true,
			mappedBy = "post")
	private List<Photo> photos;
	
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

	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

}
