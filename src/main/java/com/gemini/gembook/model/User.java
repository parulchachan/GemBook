package com.gemini.gembook.model;

//import java.util.HashSet;
//import java.util.Set;

import javax.persistence.*;



@Entity
@Table(name="users")
public class User {
	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "email_id")
	private String emailId;
	
//	@JsonIgnore
//	@OneToMany(
//			mappedBy="users", 
//			cascade = CascadeType.ALL, 
//			targetEntity=Post.class)
//	Set<Post> posts = new HashSet<>();
	
	public User(String userId, String userName, String emailId) {
		this.userId = userId;
		this.userName = userName;
		this.emailId=emailId;
	}
	
	public User() {}
	
	public User(String userId){
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUSerName(String userName) {
		this.userName = userName;
	}
	
	public String getUserId() {
	return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
//
//	@JsonIgnore
//	public Set<Post> getPosts() {
//		return posts;
//	}
//
//	public void setPosts(Set<Post> posts) {
//		this.posts = posts;
//	}
}
