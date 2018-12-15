package com.accenture.test.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "accenture_user")
public class AccentureUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
    private String username;
    private String password;
	private Integer permission;
	@OneToOne(cascade = CascadeType.ALL)
	private UserProfile up;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPermission() {
		return permission;
	}
	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	public UserProfile getUp() {
		return up;
	}
	public void setUp(UserProfile up) {
		this.up = up;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", permission=" + permission + ", up=" + up + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
