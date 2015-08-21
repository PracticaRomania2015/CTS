package com.cts.dao;

import com.cts.entities.User;

public interface UserDAOInterface {

	public boolean validateLogin(User user);

	public boolean createAccount(User user);

	public boolean deleteAccount(User user);

}
