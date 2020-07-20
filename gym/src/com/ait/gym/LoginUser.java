package com.ait.gym;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginUser {
	private int loginID;
	private String email;
	private String password;
	private String userType;

	
	
	public LoginUser() {
		
	}

	public LoginUser(int loginID, String email, String password, String userType) {
		this.loginID = loginID;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}
	
	public int getLoginID() {
		return loginID;
	}
	
	public void setLoginID(int loginID) {
		this.loginID = loginID;
	}
	
	

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}

}

