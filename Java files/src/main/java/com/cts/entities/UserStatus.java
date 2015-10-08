package com.cts.entities;

import java.util.ArrayList;

public class UserStatus {

	private int userId;
	private boolean isSysAdmin;
	private ArrayList<UserRight> categoryAdminRights;

	public UserStatus() {
		userId = 0;
		isSysAdmin = false;
		categoryAdminRights = new ArrayList<UserRight>();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isSysAdmin() {
		return isSysAdmin;
	}

	public void setSysAdmin(boolean isSysAdmin) {
		this.isSysAdmin = isSysAdmin;
	}

	public ArrayList<UserRight> getCategoryAdminRights() {
		return categoryAdminRights;
	}

	public void setCategoryAdminRights(ArrayList<UserRight> categoryAdminRights) {
		this.categoryAdminRights = categoryAdminRights;
	}
}