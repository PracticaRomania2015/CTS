package com.cts.entities;

import java.util.ArrayList;
import java.util.HashMap;

public class UserStatus {

	private int userId;
	private boolean isSysAdmin;
	private ArrayList<Integer> adminCategoryId;
	private HashMap<Category, Boolean> userCategories;

	public UserStatus() {

		userId = 0;
		isSysAdmin = false;
		adminCategoryId = new ArrayList<Integer>();
		userCategories = new HashMap<Category, Boolean>();
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

	public HashMap<Category, Boolean> getUserCategories() {

		return userCategories;
	}

	public void setUserCategories(HashMap<Category, Boolean> userCategories) {

		this.userCategories = userCategories;
	}
}