package com.gemini.gembook.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="post_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostType {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="post_type_id")
	private int postTypeId;
	
	@Column(name="post_type_name")
	private String postType;
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "postType")
	private Set<Post> posts;
	
	public PostType() {
	}
	
	public PostType(int postTypeId) {
		this.postTypeId = postTypeId;	
	}
	
	@JsonIgnore
	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
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
	
	 public void addPost(Post post){
	        this.posts.add(post);
	    }

}
