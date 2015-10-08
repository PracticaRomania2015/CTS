package com.cts.entities;

public class UserRight {

	private Category category;
	private boolean adminStatus;

	public UserRight() {
		category = new Category();
		adminStatus = false;
	}

	public UserRight(Category category, boolean status) {
		this.category = category;
		this.adminStatus = status;
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