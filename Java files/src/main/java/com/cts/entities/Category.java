package com.cts.entities;

public class Category {

	private int categoryId;
	private String categoryName;
	private int parentCategoryId;

	public Category() {
		categoryId = 0;
		categoryName = "";
		parentCategoryId = 0;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(int parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
}
