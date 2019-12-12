package com.gemini.gembook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="post_type")
public class PostType {

	@Id
	@Column(name="post_type_id")
	private int postTypeId;
	
	@Column(name="post_type_name")
	private String postType;
	
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

}
