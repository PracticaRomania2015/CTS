package com.cts.dao;

import java.util.ArrayList;
import com.cts.entities.Category;
import com.cts.entities.User;
import com.cts.entities.UserForUpdate;

public interface UserDAOInterface {

	public boolean validateLogin(User user);

	public boolean createAccount(User user);

	public boolean deleteAccount(User user);

	public boolean resetPassword(String email, String newPassword);

	public boolean updateUserPersonalData(UserForUpdate user);

	public ArrayList<User> getAdminsForCategory(Category category);
}
