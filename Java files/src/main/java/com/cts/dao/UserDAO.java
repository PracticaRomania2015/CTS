package com.cts.dao;

import java.sql.SQLException;
import com.cts.dao.BaseDAO;
import com.cts.dao.InOutParam;
import com.cts.dao.StoredProceduresNames;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.User;

public class UserDAO extends BaseDAO implements UserDAOInterface {

	@Override
	public boolean validateLogin(User user) {

		InOutParam<Integer> userIdParam = new InOutParam<Integer>(0, "UserId", true);
		InOutParam<String> firstNameParam = new InOutParam<String>("", "FirstName", true);
		InOutParam<String> lastNameParam = new InOutParam<String>("", "LastName", true);
		InOutParam<String> titleParam = new InOutParam<String>("", "Title", true);
		InOutParam<String> emailParam = new InOutParam<String>(user.getEmail(), "Email", false);
		InOutParam<String> passwordParam = new InOutParam<String>(user.getPassword(), "Password", false);
		InOutParam<Integer> errorParam = new InOutParam<Integer>(0, "Error", true);
		try {

			prepareExecution(StoredProceduresNames.ValidateLogin, userIdParam, firstNameParam, lastNameParam,
					titleParam, emailParam, passwordParam, errorParam);
			execute();
			if (callableStatement.getInt(errorParam.getName()) == 0
					&& callableStatement.getInt(userIdParam.getName()) != 0) {

				user.setUserId(callableStatement.getInt(userIdParam.getName()));
				user.setFirstName(callableStatement.getString(firstNameParam.getName()));
				user.setLastName(callableStatement.getString(lastNameParam.getName()));
				user.setTitle(callableStatement.getString(titleParam.getName()));
				callableStatement.close();
				return true;
			} else {

				callableStatement.close();
				return false;
			}
		} catch (SQLException e) {

			return false;
		}
	}

	@Override
	public boolean createAccount(User user) {

		if (user.getPassword().length() < 6) {
			return false;
		}

		try {

			InOutParam<String> firstNameParam = new InOutParam<String>(user.getFirstName(), "FirstName", false);
			InOutParam<String> lastNameParam = new InOutParam<String>(user.getLastName(), "LastName", false);
			InOutParam<String> titleParam = new InOutParam<String>(user.getTitle(), "Title", false);
			InOutParam<String> emailParam = new InOutParam<String>(user.getEmail(), "Email", false);
			InOutParam<String> passwordParam = new InOutParam<String>(user.getPassword(), "Password", false);
			InOutParam<Integer> errorParam = new InOutParam<Integer>(0, "Error", true);
			prepareExecution(StoredProceduresNames.CreateUser, firstNameParam, lastNameParam, titleParam, emailParam,
					passwordParam, errorParam);
			execute();

			if (callableStatement.getInt(errorParam.getName()) == 0) {
				callableStatement.close();
				return true;
			} else {
				callableStatement.close();
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean deleteAccount(User user) {

		try {

			InOutParam<String> emailParam = new InOutParam<String>(user.getEmail(), "Email", false);
			prepareExecution(StoredProceduresNames.DeleteUser, emailParam);
			execute();
			callableStatement.close();
		} catch (Exception e) {

			return false;
		}
		return true;
	}
}
