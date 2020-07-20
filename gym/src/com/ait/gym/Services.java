package com.ait.gym;

import java.util.Date;

public class Services {

	private int serviceID;
	private String serviceName;
	private double servicePrice;
	private int serviceLimit;
	private String serviceInstructorFirst;
	private String serviceInstructorLast;
	private int categoryID;
	private Date startDate;
	private Date endDate;
	
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getServiceID() {
		return serviceID;
	}
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public double getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}
	public int getServiceLimit() {
		return serviceLimit;
	}
	public void setServiceLimit(int serviceLimit) {
		this.serviceLimit = serviceLimit;
	}
	public String getServiceInstructorFirst() {
		return serviceInstructorFirst;
	}
	public void setServiceInstructorFirst(String serviceInstructorFirst) {
		this.serviceInstructorFirst = serviceInstructorFirst;
	}
	public String getServiceInstructorLast() {
		return serviceInstructorLast;
	}
	public void setServiceInstructorLast(String serviceInstructorLast) {
		this.serviceInstructorLast = serviceInstructorLast;
	}
	
	
}
