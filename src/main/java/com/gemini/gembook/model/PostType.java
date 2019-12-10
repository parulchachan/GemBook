package com.gemini.gembook.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="post_type")
public class PostType {

	@Id
	@Column(name="post_type_id")
	private int postTypeId;
	
	@Column(name="post_type_name")
	private String postType;
	
//	@JsonIgnore
//	@OneToMany(mappedBy="postType", cascade = CascadeType.ALL, targetEntity=Post.class)
//	Set<Post> posts = new HashSet<>();

	public PostType(int postTypeId, String postType) {
		this.postTypeId = postTypeId;
		this.postType = postType;
	}
	
	public PostType() {
	}
	
	public PostType(int postTypeId) {
		this.postTypeId = postTypeId;
		
	}
	
	public int getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(int postTypeId) {
		this.postTypeId = postTypeId;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}
	
//	@JsonIgnore
//	public Set<Post> getPosts() {
//		return posts;
//	}
//
//	public void setPosts(Set<Post> posts) {
//		this.posts = posts;
//	}
	
}
