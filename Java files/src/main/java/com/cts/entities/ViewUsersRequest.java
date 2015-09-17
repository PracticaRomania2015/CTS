package com.cts.entities;

import java.util.ArrayList;

public class ViewUsersRequest {

	private int userId;
	private boolean isSysAdmin;
	private ArrayList<Integer> adminCategoryId;

	public ViewUsersRequest() {

		userId = 0;
		isSysAdmin = false;
		adminCategoryId = new ArrayList<Integer>();
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

	public ArrayList<Integer> getAdminCategoryId() {
		return adminCategoryId;
	}

	public void setAdminCategoryId(ArrayList<Integer> adminCategoryId) {
		this.adminCategoryId = adminCategoryId;
	}
}