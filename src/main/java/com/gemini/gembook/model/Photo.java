package com.gemini.gembook.model;

import java.io.IOException;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.io.Files;

@Entity
@Table( name="photos")
public class Photo {
	
	@Id
	@Column(name="photo_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int photoId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="post_id")
	private Post post;
	
	@Lob
	@Column(name="photo_content")
	private byte[] photoContent;
	
	@Column(name="photo_time")
	private long photoTime;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_type")
	private String fileType;
	
	public Photo() {
	}

	public Photo(int postId, MultipartFile files) {
		this.post = new Post(postId);
		try {
			this.photoContent = files.getBytes();
			this.fileName =files.getOriginalFilename(); 
			this.fileType = Files.getFileExtension(files.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Date date=new Date();
		this.photoTime = date.getTime();
	}
	
	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	@JsonIgnore
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public byte[] getPhotoContent() {
		return photoContent;
	}

	public void setPhotoContent(byte[] photoContent) {
		this.photoContent = photoContent;
	}

	public long getPhotoTime() {
		return photoTime;
	}

	public void setPhotoTime(long photoTime) {
		this.photoTime = photoTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	

}
