package com.ait.gym;

import java.util.Date;

public class UserAccount {
	
	private int custID; //foreign key
	private int userAccountID;
	private double amountPaid;
	private Date datePaid;
	private boolean bonusCredit;
	private String remark;
	
	public int getCustID() {
		return custID;
	}
	public Date getDatePaid() {
		return datePaid;
	}
	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}
	public void setCustID(int custID) {
		this.custID = custID;
	}
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getUserAccountID() {
		return userAccountID;
	}
	public void setUserAccountID(int userAccountID) {
		this.userAccountID = userAccountID;
	}
	public boolean isBonusCredit() {
		return bonusCredit;
	}
	public void setBonusCredit(boolean bonusCredit) {
		this.bonusCredit = bonusCredit;
	}
	

}
