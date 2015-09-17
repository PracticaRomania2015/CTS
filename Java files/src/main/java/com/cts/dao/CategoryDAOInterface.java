package com.cts.dao;

import java.util.ArrayList;
import com.cts.entities.Category;
import com.cts.entities.UserStatus;

public interface CategoryDAOInterface {

	public ArrayList<Category> getCategories();

	public ArrayList<Category> getSubcategories(Category category);

	public boolean addCategory(Category category);

	public boolean deleteCategory(Category category);
	
	public boolean getCategoriesRightsForUser(UserStatus userStatus);
}