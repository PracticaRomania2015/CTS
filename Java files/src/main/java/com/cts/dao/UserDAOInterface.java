package com.cts.dao;

import com.cts.entities.User;
import com.cts.entities.UserForUpdate;

public interface UserDAOInterface {

	public boolean validateLogin(User user);

	public boolean createAccount(User user);

	public boolean deleteAccount(User user);

	public boolean checkEmailForRecoveryPassword(String email, String newPassword);

	public boolean updateUserPersonalData(UserForUpdate user);
}
