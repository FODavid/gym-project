package com.ait.gym;

import java.util.Date;

public class Memberships {
	
	private int membID;
	private int custID;
	private boolean paid;
	private Date dateJoined;
	private double amountPaid;
	 
	
	
	public Date getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}
	public int getMembID() {
		return membID;
	}
	public void setMembID(int membID) {
		this.membID = membID;
	}
	public int getCustID() {
		return custID;
	}
	public void setCustID(int custID) {
		this.custID = custID;
	}
	
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public double getamountPaid() {
		return amountPaid;
	}
	public void setamountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	

}

