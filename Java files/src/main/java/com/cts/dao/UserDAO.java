package com.cts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cts.entities.Category;
import com.cts.entities.CategoryNotificationsSetting;
import com.cts.entities.Role;
import com.cts.entities.Ticket;
import com.cts.entities.User;
import com.cts.entities.UserForUpdate;
import com.cts.entities.UserNotificationsSettings;
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
			InOutParam<Integer> question_1_IdParam = new InOutParam<Integer>(user.getQuestion_1().getQuestionId(),
					"Question_1_Id");
			InOutParam<String> question_1_AnswerParam = new InOutParam<String>(user.getQuestionAnswer_1(),
					"Question_1_Answer");
			InOutParam<Integer> question_2_IdParam = new InOutParam<Integer>(user.getQuestion_2().getQuestionId(),
					"Question_2_Id");
			InOutParam<String> question_2_AnswerParam = new InOutParam<String>(user.getQuestionAnswer_2(),
					"Question_2_Answer");
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.CreateUser, firstNameParam, lastNameParam, titleParam, emailParam,
					passwordParam, question_1_IdParam, question_1_AnswerParam, question_2_IdParam,
					question_2_AnswerParam, errCodeParam);
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
	public boolean resetPassword(User user) {

		try {

			InOutParam<String> emailParam = new InOutParam<String>(user.getEmail(), "Email");
			InOutParam<String> passwordParam = new InOutParam<String>(user.getPassword(), "Password");
			InOutParam<Integer> question_1_IdParam = new InOutParam<Integer>(user.getQuestion_1().getQuestionId(),
					"Question_1_Id");
			InOutParam<String> question_1_AnswerParam = new InOutParam<String>(user.getQuestionAnswer_1(),
					"Question_1_Answer");
			InOutParam<Integer> question_2_IdParam = new InOutParam<Integer>(user.getQuestion_2().getQuestionId(),
					"Question_2_Id");
			InOutParam<String> question_2_AnswerParam = new InOutParam<String>(user.getQuestionAnswer_2(),
					"Question_2_Answer");
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.ResetPassword, emailParam, passwordParam, question_1_IdParam,
					question_1_AnswerParam, question_2_IdParam, question_2_AnswerParam, errCodeParam);
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
			for (int i = 0; i < userStatus.getCategoryAdminRights().size(); i++) {

				if (userStatus.getCategoryAdminRights().get(i).isAdminStatus())
					categoryIdList += userStatus.getCategoryAdminRights().get(i).getCategory().getCategoryId() + ",";
			}

			if (categoryIdList.length() > 0 && categoryIdList.substring(categoryIdList.length() - 1).equals(",")) {

				categoryIdList = categoryIdList.substring(0, categoryIdList.length() - 1);
			}

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
				role.setRoleName(resultSet.getString("RoleName"));
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

	@Override
	public User getTicketUser(Ticket ticket) {

		User user = null;
		try {

			InOutParam<Integer> ticketIdParam = new InOutParam<Integer>(ticket.getTicketId(), "TicketId");
			InOutParam<Integer> userIdParam = new InOutParam<Integer>(0, "UserId", true);
			InOutParam<String> firstNameParam = new InOutParam<String>("", "FirstName", true);
			InOutParam<String> lastNameParam = new InOutParam<String>("", "LastName", true);
			InOutParam<String> emailParam = new InOutParam<String>("", "Email", true);
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.GetTicketUser, ticketIdParam, userIdParam, firstNameParam,
					lastNameParam, emailParam, errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 0) {

				user = new User();
				user.setUserId(userIdParam.getParameter());
				user.setFirstName(firstNameParam.getParameter());
				user.setLastName(lastNameParam.getParameter());
				user.setEmail(emailParam.getParameter());
			}
		} catch (SQLException e) {

			return user;
		} finally {

			closeCallableStatement();
		}
		return user;
	}

	@Override
	public boolean getUserData(User user) {

		try {

			InOutParam<Integer> userIdParam = new InOutParam<Integer>(user.getUserId(), "UserId");
			InOutParam<String> firstNameParam = new InOutParam<String>("", "FirstName", true);
			InOutParam<String> lastNameParam = new InOutParam<String>("", "LastName", true);
			InOutParam<String> titleParam = new InOutParam<String>("", "Title", true);
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.GetUserData, userIdParam, firstNameParam, lastNameParam, titleParam,
					errCodeParam);
			execute();
			if (errCodeParam.getParameter() == 0) {

				user.setUserId(user.getUserId());
				user.setFirstName(firstNameParam.getParameter());
				user.setLastName(lastNameParam.getParameter());
				user.setTitle(titleParam.getParameter());
			}
		} catch (SQLException e) {

			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}

	@Override
	public boolean getUserNotificationsSettings(UserNotificationsSettings userNotificationsSettings) {

		try {

			InOutParam<Integer> userIdParam = new InOutParam<Integer>(userNotificationsSettings.getUser().getUserId(),
					"UserId");
			InOutParam<Boolean> getEmailForTicketResponseParam = new InOutParam<Boolean>(false,
					"GetEmailForTicketResponse", true);
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.GetUserNotificationsSettings, userIdParam,
					getEmailForTicketResponseParam, errCodeParam);
			ResultSet resultSet = execute(true);
			ArrayList<CategoryNotificationsSetting> categoriesNotificationsSettings = new ArrayList<CategoryNotificationsSetting>();
			while (resultSet.next()) {
				
				CategoryNotificationsSetting categoryNotificationsSetting = new CategoryNotificationsSetting();
				Category category = new Category();
				category.setCategoryId(resultSet.getInt("CategoryId"));
				category.setCategoryName(resultSet.getString("CategoryName"));
				categoryNotificationsSetting.setCategory(category);
				categoryNotificationsSetting.setGetEmailForNewTicket(resultSet.getBoolean("GetEmailForNewTicket"));
				categoryNotificationsSetting.setGetEmailForNewComment(resultSet.getBoolean("GetEmailForNewComment"));
				categoriesNotificationsSettings.add(categoryNotificationsSetting);
			}
			userNotificationsSettings.setCategoriesNotificationsSettings(categoriesNotificationsSettings);
			setOutParametersAfterExecute();
			if (errCodeParam.getParameter() != 0) {

				return false;
			}
			userNotificationsSettings.setGetEmailForTicketResponse(getEmailForTicketResponseParam.getParameter());
		} catch (SQLException e) {
			
			return false;
		} finally {

			closeCallableStatement();
		}
		return true;
	}

	@Override
	public boolean updateUserNotificationsSettings(UserNotificationsSettings userNotificationsSettings) {

		try {

			String categoryIdListForGetEmailForNewTicket = "";
			String categoryIdListForGetEmailForNewComment = "";
			for (int i = 0; i < userNotificationsSettings.getCategoriesNotificationsSettings().size(); i++) {

				if (userNotificationsSettings.getCategoriesNotificationsSettings().get(i).isGetEmailForNewTicket())
					categoryIdListForGetEmailForNewTicket += userNotificationsSettings
							.getCategoriesNotificationsSettings().get(i).getCategory().getCategoryId() + ",";
				if (userNotificationsSettings.getCategoriesNotificationsSettings().get(i).isGetEmailForNewComment())
					categoryIdListForGetEmailForNewComment += userNotificationsSettings
							.getCategoriesNotificationsSettings().get(i).getCategory().getCategoryId() + ",";
			}

			if (categoryIdListForGetEmailForNewTicket.length() > 0 && categoryIdListForGetEmailForNewTicket
					.substring(categoryIdListForGetEmailForNewTicket.length() - 1).equals(",")) {

				categoryIdListForGetEmailForNewTicket = categoryIdListForGetEmailForNewTicket.substring(0,
						categoryIdListForGetEmailForNewTicket.length() - 1);
			}

			if (categoryIdListForGetEmailForNewComment.length() > 0 && categoryIdListForGetEmailForNewComment
					.substring(categoryIdListForGetEmailForNewComment.length() - 1).equals(",")) {

				categoryIdListForGetEmailForNewComment = categoryIdListForGetEmailForNewComment.substring(0,
						categoryIdListForGetEmailForNewComment.length() - 1);
			}

			InOutParam<Integer> userIdParam = new InOutParam<Integer>(userNotificationsSettings.getUser().getUserId(),
					"UserId");
			InOutParam<Boolean> getEmailForTicketResponseParam = new InOutParam<Boolean>(
					userNotificationsSettings.isGetEmailForTicketResponse(), "GetEmailForTicketResponse");
			InOutParam<String> categoryIdListForGetEmailForNewTicketParam = new InOutParam<String>(
					categoryIdListForGetEmailForNewTicket, "CategoryIdListForGetEmailForNewTicket");
			InOutParam<String> categoryIdListForGetEmailForNewCommentParam = new InOutParam<String>(
					categoryIdListForGetEmailForNewComment, "CategoryIdListForGetEmailforNewComment");
			InOutParam<Integer> errCodeParam = new InOutParam<Integer>(0, "ErrCode", true);
			prepareExecution(StoredProceduresNames.UpdateUserNotificationsSettings, userIdParam,
					getEmailForTicketResponseParam, categoryIdListForGetEmailForNewTicketParam,
					categoryIdListForGetEmailForNewCommentParam, errCodeParam);
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
}