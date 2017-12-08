package mum.mpp.carrental.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import mum.mpp.carrental.enums.UserRoleEnum;

@MappedSuperclass
public abstract class User {
	// DB Fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	private String userName;
	private String password;
	private UserRoleEnum userRole;
	// End DB Fields

	public User() {}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserRole(UserRoleEnum userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public UserRoleEnum getUserRole() {
		return this.userRole;
	}
	
	public User(String userName, String password, UserRoleEnum role) {
		this.userName = userName;
		this.password = password;
		this.userRole = role;
	}
	
	public void logIn() {
		
	}
	
	public void logOut() {
		
	}
	
	public abstract void print();
}
