package com.gemini.gembook.model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	public User(String firstName, String lastName, String userId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
	}
	
	public User() {}
	
	public User(String userId){
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
