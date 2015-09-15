package com.cts.dao;

import java.sql.SQLException;

import com.cts.entities.User;
import com.cts.entities.UserForUpdate;

public class UserDAO extends BaseDAO implements UserDAOInterface {

	@Override
	public boolean validateLogin(User user) {

		InOutParam<Integer> userIdParam = new InOutParam<Integer>(0, "UserId", true);
		InOutParam<String> firstNameParam = new InOutParam<String>("", "FirstName", true);
		InOutParam<String> lastNameParam = new InOutParam<String>("", "LastName", true);
		InOutParam<String> titleParam = new InOutParam<String>("", "Title", true);
		InOutParam<String> emailParam = new InOutParam<String>(user.getEmail(), "Email");
		InOutParam<String> passwordParam = new InOutParam<String>(user.getPassword(), "Password");
		InOutParam<Integer> roleIdParam = new InOutParam<Integer>(0, "RoleId", true);
		InOutParam<String> roleNameParam = new InOutParam<String>("", "RoleName", true);
		InOutParam<Integer> errorParam = new InOutParam<Integer>(0, "Error", true);
		try {

			prepareExecution(StoredProceduresNames.ValidateLogin, userIdParam, firstNameParam, lastNameParam,
					titleParam, emailParam, passwordParam, roleIdParam, roleNameParam, errorParam);
			execute();

			if (errorParam.getParameter() == 0 && userIdParam.getParameter() != 0) {

				user.setUserId(userIdParam.getParameter());
				user.setFirstName(firstNameParam.getParameter());
				user.setLastName(lastNameParam.getParameter());
				user.setTitle(titleParam.getParameter());
				user.getRole().setRoleId(roleIdParam.getParameter());
				user.getRole().setRoleName(roleNameParam.getParameter());
				return true;
			} else {

				return false;
			}
		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
		}
	}

	@Override
	public boolean createAccount(User user) {

		try {

			InOutParam<String> firstNameParam = new InOutParam<String>(user.getFirstName(), "FirstName");
			InOutParam<String> lastNameParam = new InOutParam<String>(user.getLastName(), "LastName");
			InOutParam<String> titleParam = new InOutParam<String>(user.getTitle(), "Title");
			InOutParam<String> emailParam = new InOutParam<String>(user.getEmail(), "Email");
			InOutParam<String> passwordParam = new InOutParam<String>(user.getPassword(), "Password");
			InOutParam<Integer> errorParam = new InOutParam<Integer>(0, "Error", true);
			prepareExecution(StoredProceduresNames.CreateUser, firstNameParam, lastNameParam, titleParam, emailParam,
					passwordParam, errorParam);
			execute();

			if (errorParam.getParameter() == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		} finally {

			closeCallableStatement();
		}
	}

	@Override
	public boolean deleteAccount(User user) {

		try {

			InOutParam<Integer> userIdParam = new InOutParam<Integer>(user.getUserId(), "UserId");
			prepareExecution(StoredProceduresNames.DeleteUser, userIdParam);
			execute();
		} catch (Exception e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}

	@Override
	public boolean resetPassword(String email, String newPassword) {

		try {

			InOutParam<String> emailParam = new InOutParam<String>(email, "Email");
			InOutParam<String> passwordParam = new InOutParam<String>(newPassword, "Password");
			InOutParam<Integer> errorParam = new InOutParam<Integer>(0, "Error", true);
			prepareExecution(StoredProceduresNames.ResetPassword, emailParam, passwordParam, errorParam);
			execute();
			if (errorParam.getParameter() == 0) {

				return true;
			} else {

				return false;
			}
		} catch (Exception e) {

			return false;
		} finally {

			closeCallableStatement();
		}
	}

	@Override
	public boolean updateUserPersonalData(UserForUpdate user) {

		try {

			InOutParam<Integer> userIdParam = new InOutParam<Integer>(user.getUserId(), "UserId");
			InOutParam<String> firstNameParam = new InOutParam<String>(user.getFirstName(), "FirstName");
			InOutParam<String> lastNameParam = new InOutParam<String>(user.getLastName(), "LastName");
			InOutParam<String> titleParam = new InOutParam<String>(user.getTitle(), "Title");
			InOutParam<String> newPasswordParam = new InOutParam<String>(user.getPassword(), "NewPassword");
			InOutParam<String> oldPasswordParam = new InOutParam<String>(user.getOldPassword(), "OldPassword");
			InOutParam<Integer> errorParam = new InOutParam<Integer>(0, "Error", true);
			prepareExecution(StoredProceduresNames.UpdateUser, userIdParam, firstNameParam, lastNameParam, titleParam,
					newPasswordParam, oldPasswordParam, errorParam);
			execute();
			if (errorParam.getParameter() == 0) {

				return true;
			} else {

				return false;
			}
		} catch (Exception e) {

			return false;
		} finally {

			closeCallableStatement();
		}
	}
}
