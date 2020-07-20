package com.ait.gym;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customers {

	private int custID;
	private String custFirst;
	private String custLast;
	private String custPhone;
	private String custEmail;
	private String custPass;
	private boolean custMember;
	private int categoryID; //foreign key
	private double cusBalance;
	private int loginID;
	
	
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public double getcusBalance() {
		return cusBalance;
	}
	public void setcusBalance(double cusBalance) {
		this.cusBalance = cusBalance;
	}
	public int getCustID() {
		return custID;
	}
	public void setCustID(int custID) {
		this.custID = custID;
	}
	public String getCustFirst() {
		return custFirst;
	}
	public void setCustFirst(String custFirst) {
		this.custFirst = custFirst;
	}
	public String getCustLast() {
		return custLast;
	}
	public void setCustLast(String custLast) {
		this.custLast = custLast;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public String getCustPass() {
		return custPass;
	}
	public void setCustPass(String custPass) {
		this.custPass = custPass;
	}
	public boolean isCustMember() {
		return custMember;
	}
	public void setCustMember(boolean custMember) {
		this.custMember = custMember;
	}
	public int getloginID() {
		return loginID;
	}
	public void setloginID(int loginID) {
		this.loginID = loginID;
	}
}
