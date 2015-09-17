package com.cts.dao;

import java.util.ArrayList;
import com.cts.entities.Category;
import com.cts.entities.User;
import com.cts.entities.UserForUpdate;
import com.cts.entities.UserStatus;
import com.cts.entities.ViewUsersRequest;

public interface UserDAOInterface {

	public boolean validateLogin(User user);

	public boolean createAccount(User user);

	public boolean deleteAccount(User user);

	public boolean resetPassword(String email, String newPassword);

	public boolean updateUserPersonalData(UserForUpdate user);

	public ArrayList<User> getAdminsForCategory(Category category);

	public boolean updateUserStatus(UserStatus userStatus);

	public ArrayList<User> getUsers(ViewUsersRequest viewUsersRequest, StringBuilder totalNumberOfPages);
}
