package com.ait.gym;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class Category {
	private int categoryID;
	private String categoryName;
	
	
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
