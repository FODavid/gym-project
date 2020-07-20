package com.ait.gym;

import java.util.Date;

public class UserClasses {
	
	private int userClassesID;
	private int custID;
	private int serviceID;
	private Date startDate;
	private Date endDate;
	private double expectedAmount;
	private double originalAmount;
	private boolean walkinCus;

	
	
	public int getUserClassesID() {
		return userClassesID;
	}
	public void setUserClassesID(int userClassesID) {
		this.userClassesID = userClassesID;
	}
	public int getCustID() {
		return custID;
	}
	public void setCustID(int custID) {
		this.custID = custID;
	}
	public int getServiceID() {
		return serviceID;
	}
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public double getExpectedAmount() {
		return expectedAmount;
	}
	public void setExpectedAmount(double expectedAmount) {
		this.expectedAmount = expectedAmount;
	}
	public double getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(double originalAmount) {
		this.originalAmount = originalAmount;
	}
	public boolean isWalkinCus() {
		return walkinCus;
	}
	public void setWalkinCus(boolean walkinCus) {
		this.walkinCus = walkinCus;
	}
	
	
	
	

}
