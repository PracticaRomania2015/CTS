package com.cts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cts.entities.Category;
import com.cts.entities.UserRight;
import com.cts.entities.UserStatus;

public class CategoryDAO extends BaseDAO implements CategoryDAOInterface {

	@Override
	public ArrayList<Category> getCategories() {

		ArrayList<Category> categories = new ArrayList<Category>();
		try {

			prepareExecution(StoredProceduresNames.GetCategories);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				Category category = new Category();
				category.setCategoryId(resultSet.getInt("CategoryId"));
				category.setCategoryName(resultSet.getString("CategoryName"));
				category.setParentCategoryId(resultSet.getInt("ParentCategoryId"));
				categories.add(category);
			}
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}
		return categories;
	}

	@Override
	public ArrayList<Category> getSubcategories(Category category) {

		ArrayList<Category> subcategories = new ArrayList<Category>();
		try {

			InOutParam<Integer> categoryIdParam = new InOutParam<Integer>(category.getCategoryId(), "CategoryId");
			prepareExecution(StoredProceduresNames.GetSubcategories, categoryIdParam);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				Category subcategory = new Category();
				subcategory.setCategoryId(resultSet.getInt("CategoryId"));
				subcategory.setCategoryName(resultSet.getString("CategoryName"));
				subcategory.setParentCategoryId(category.getCategoryId());
				subcategories.add(subcategory);
			}
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}

		return subcategories;
	}

	@Override
	public boolean addCategory(Category category) {

		try {

			InOutParam<String> categoryNameParam = new InOutParam<String>(category.getCategoryName(), "CategoryName");
			InOutParam<Integer> parentCategoryIdParam = new InOutParam<Integer>(category.getParentCategoryId(),
					"ParentCategoryId");
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.AddCategory, categoryNameParam, parentCategoryIdParam, errCodeParam);
			execute();
			if (errCodeParam.getParameter() != 0){
				
				return false;
			}
		} catch (Exception e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}

	@Override
	public boolean deleteCategory(Category category) {

		try {

			InOutParam<Integer> categoryIdParam = new InOutParam<Integer>(category.getCategoryId(), "CategoryId");
			prepareExecution(StoredProceduresNames.DeleteCategory, categoryIdParam);
			execute();
		} catch (Exception e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}

	@Override
	public boolean viewCategoriesRightsForUser(UserStatus userStatus) {

		ArrayList<UserRight> userCategories = new ArrayList<UserRight>();
		try {

			InOutParam<Integer> userIdParam = new InOutParam<Integer>(userStatus.getUserId(), "UserId");
			InOutParam<Boolean> isSysAdminParam = new InOutParam<Boolean>(false, "IsSysAdmin", true);
			prepareExecution(StoredProceduresNames.GetCategoriesRightsForUser, userIdParam, isSysAdminParam);
			ResultSet resultSet = execute(true);
			while (resultSet.next()) {
				
				Category category = new Category();
				category.setCategoryId(resultSet.getInt("CategoryId"));
				category.setCategoryName(resultSet.getString("CategoryName"));
				if (resultSet.getString("CategoryUserId") != null) {
					
					userCategories.add(new UserRight(category, true));
				} else {

					userCategories.add(new UserRight(category, false));
				}
			}
			userStatus.setCategoryAdminRights(userCategories);
			setOutParametersAfterExecute();
			userStatus.setSysAdmin(isSysAdminParam.getParameter());

		} catch (Exception e) {
			
			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}
}
