package com.jobportal.JobApplicationTracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int user_id;
	
	@NotEmpty(message="User Name can not be Empty!")
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotNull(message="Email shoukd not be null!")
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "status")
	private boolean status;
	
	@Column(name = "created_at")
	private String createdAt;
	
	
	public User() {}
	
	public User(String name, String email, String role, boolean status, String createdAt) {
		this.name = name;
		this.email = email;
		this.role = role;
		this.status = status;
		this.createdAt = createdAt;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
}
