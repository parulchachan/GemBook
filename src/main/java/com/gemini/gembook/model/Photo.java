package com.gemini.gembook.model;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name="photo")
public class Photo {
	
	@Id
	@Column(name="photo_id")
	private int photoId;
	
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post postId;
	
	private Blob photoContent;
	
	private long photoTime;

	public Photo(int postId, Blob photoContent) {
		this.postId = new Post(postId);
		this.photoContent = photoContent;
		Date date=new Date();
		this.photoTime = date.getTime();
		
	}
	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public Post getPostId() {
		return postId;
	}

	public void setPostId(Post postId) {
		this.postId = postId;
	}

	public Blob getPhotoContent() {
		return photoContent;
	}

	public void setPhotoContent(Blob photoContent) {
		this.photoContent = photoContent;
	}

	public long getPhotoTime() {
		return photoTime;
	}

	public void setPhotoTime(long photoTime) {
		this.photoTime = photoTime;
	}
}
