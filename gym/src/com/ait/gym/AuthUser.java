package com.ait.gym;

public class AuthUser {
	private LoginUser loginUser;
	private Customers customer;
	
	public AuthUser(LoginUser user, Customers cust) {
		this.setCustomer(cust);
		this.setLoginUser(user);
	}
	
	public LoginUser getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}
	public Customers getCustomer() {
		return customer;
	}
	public void setCustomer(Customers customer) {
		this.customer = customer;
	}
}
