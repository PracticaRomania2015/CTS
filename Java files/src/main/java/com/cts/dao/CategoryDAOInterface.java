package com.cts.dao;

import java.util.ArrayList;
import com.cts.entities.Category;

public interface CategoryDAOInterface {

	public ArrayList<Category> getCategories();

	public ArrayList<Category> getSubcategories(Category category);

	public boolean addCategory(Category category);

	public boolean deleteCategory(Category category);
}
