package com.cts.entities;

public class UserRight {

	private Category category;
	private boolean adminStatus;
	
	public UserRight() {

		setCategory(null);
		setAdminStatus(false);
	}
	
	public UserRight(Category c, boolean b) {

		setCategory(c);
		setAdminStatus(b);
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(boolean adminStatus) {
		this.adminStatus = adminStatus;
	}
	
}
