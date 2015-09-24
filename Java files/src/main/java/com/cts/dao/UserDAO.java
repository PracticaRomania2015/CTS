package com.cts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.cts.entities.Category;
import com.cts.entities.Role;
import com.cts.entities.User;
import com.cts.entities.UserForUpdate;
import com.cts.entities.UserStatus;
import com.cts.entities.ViewUsersRequest;

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
		InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
		try {

			prepareExecution(StoredProceduresNames.ValidateLogin, userIdParam, firstNameParam, lastNameParam,
					titleParam, emailParam, passwordParam, roleIdParam, roleNameParam, errCodeParam);
			execute();

			if (errCodeParam.getParameter() == 0 && userIdParam.getParameter() != 0) {

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
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.CreateUser, firstNameParam, lastNameParam, titleParam, emailParam,
					passwordParam, errCodeParam);
			execute();

			if (errCodeParam.getParameter() == 0) {
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
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.ResetPassword, emailParam, passwordParam, errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 0) {

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
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.UpdateUser, userIdParam, firstNameParam, lastNameParam, titleParam,
					newPasswordParam, oldPasswordParam, errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 0) {

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
	public ArrayList<User> getAdminsForCategory(Category category) {

		ArrayList<User> admins = new ArrayList<User>();
		try {

			InOutParam<Integer> categoryIdParam = new InOutParam<Integer>(category.getCategoryId(), "CategoryId");
			prepareExecution(StoredProceduresNames.GetAdminsForCategory, categoryIdParam);
			ResultSet resultSet = execute();
			while (resultSet.next()) {

				User admin = new User();
				admin.setUserId(resultSet.getInt("UserId"));
				admin.setFirstName(resultSet.getString("FirstName"));
				admin.setLastName(resultSet.getString("LastName"));
				admins.add(admin);
			}
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}
		return admins;
	}

	@Override
	public boolean updateUserStatus(UserStatus userStatus) {

		try {

			String categoryIdList = "";
			for (int i = 0; i < userStatus.getCategoryAdminRights().size() - 1; i++) {

				if (userStatus.getCategoryAdminRights().get(i).isAdminStatus())
					categoryIdList += userStatus.getCategoryAdminRights().get(i).getCategory().getCategoryId() + ",";
			}
			if (userStatus.getCategoryAdminRights().get(userStatus.getCategoryAdminRights().size() - 1).isAdminStatus())
				categoryIdList += userStatus.getCategoryAdminRights()
						.get(userStatus.getCategoryAdminRights().size() - 1).getCategory().getCategoryId();

			InOutParam<Integer> userIdParam = new InOutParam<Integer>(userStatus.getUserId(), "UserId");
			InOutParam<Boolean> isSysAdminParam = new InOutParam<Boolean>(userStatus.isSysAdmin(), "IsSysAdmin");
			InOutParam<String> categoryIdListParam = new InOutParam<String>(categoryIdList, "CategoryIdList");
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.UpdateCategoriesRightsForUser, userIdParam, isSysAdminParam,
					categoryIdListParam, errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 0) {

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
	public ArrayList<User> getUsers(ViewUsersRequest viewUsersRequest, StringBuilder totalNumberOfPages) {

		ArrayList<User> users = new ArrayList<User>();
		try {

			InOutParam<Integer> requestedPageNumberParam = new InOutParam<Integer>(
					viewUsersRequest.getRequestedPageNumber(), "RequestedPageNumber");
			InOutParam<Integer> usersPerPageParam = new InOutParam<Integer>(viewUsersRequest.getUsersPerPage(),
					"UsersPerPage");
			InOutParam<String> textToSearchParam = new InOutParam<String>(viewUsersRequest.getTextToSearch(),
					"TextToSearch");
			InOutParam<String> searchTypeParam = new InOutParam<String>(viewUsersRequest.getSearchType(), "SearchType");
			InOutParam<String> sortTypeParam = new InOutParam<String>(viewUsersRequest.getSortType(), "SortType");
			InOutParam<Boolean> isSearchASCParam = new InOutParam<Boolean>(viewUsersRequest.isSearchASC(),
					"IsSearchASC");
			InOutParam<Integer> totalNumberOfPagesParam = new InOutParam<Integer>(0, "TotalNumberOfPages", true);
			prepareExecution(StoredProceduresNames.GetUsers, requestedPageNumberParam, usersPerPageParam,
					textToSearchParam, searchTypeParam, sortTypeParam, isSearchASCParam, totalNumberOfPagesParam);
			ResultSet resultSet = execute(true);
			while (resultSet.next()) {

				User user = new User();
				user.setUserId(resultSet.getInt("UserId"));
				user.setFirstName(resultSet.getString("FirstName"));
				user.setLastName(resultSet.getString("LastName"));
				user.setEmail(resultSet.getString("Email"));
				Role role = new Role();
				role.setRoleName(resultSet.getString("Role"));
				user.setRole(role);
				users.add(user);
			}
			setOutParametersAfterExecute();
			totalNumberOfPages.append(totalNumberOfPagesParam.getParameter());
		} catch (SQLException e) {
		} finally {

			closeCallableStatement();
		}
		return users;
	}
}