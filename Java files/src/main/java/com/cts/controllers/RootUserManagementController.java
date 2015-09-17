package com.cts.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.ResponseValues;
import com.cts.communication.UserRightsResponse;
import com.cts.dao.CategoryDAO;
import com.cts.dao.CategoryDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.User;
import com.cts.entities.UserStatus;
import com.cts.entities.ViewUsersRequest;

public class RootUserManagementController {
	
	private static Logger logger = Logger.getLogger(RootUserManagementController.class.getName());

	// Get a table of users.
	@RequestMapping(value = "/viewUsers", method = RequestMethod.POST)
	public @ResponseBody String viewUsers(@RequestBody ViewUsersRequest viewUserRequest) {

		logger.info("Attempting to send the list of user ...");

		UserDAOInterface userDAO = new UserDAO();
		StringBuilder totalNumberOfPages = new StringBuilder();
		ArrayList<User> users = userDAO.getUsers(viewUserRequest, totalNumberOfPages);
		String usersJson;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			usersJson = objectMapper.writeValueAsString(users);
			logger.info("The list of users was successfully retrieved!");
			return "{\"totalNumberOfPages\":" + totalNumberOfPages + ",\"users\":" + usersJson + "}";
		} catch (IOException e) {

			logger.error("Json error when trying to map the array of users.");
			return new UserRightsResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
	}
	
	// Get the rights for a user.
	@RequestMapping(value = "/getUserAdminStatus", method = RequestMethod.POST)
	public @ResponseBody String getUserAdminStatus(@RequestBody UserStatus userStatus) {

		logger.info("Attempting to get user rights ...");
		
		CategoryDAOInterface userCategRightsDAO = new CategoryDAO();
		if (userCategRightsDAO.viewCategoriesRightsForUser(userStatus)) {
			String userRightsStatus;
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				userRightsStatus = objectMapper.writeValueAsString(userStatus);
				logger.info("The list of rights was successfully retrieved!");
				// System.out.println("{\"rights\":" + userRightsStatus + "}"); // If it fails here is where blows up !
				return "{\"rights\":" + userRightsStatus + "}";
			} catch (IOException e) {
				logger.error("Json error when trying to map the array of rights.");
				return new UserRightsResponse().getMessageJson(ResponseValues.UNKNOWN);
			}
		}
		else {
			return new UserRightsResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}
	
	// Save the altered rights.
	@RequestMapping(value = "/setUserAdminStatus", method = RequestMethod.POST)
	public @ResponseBody String setUserAdminStatus(@RequestBody UserStatus userStatus) {

		logger.info("Attempting to set user rights ...");
			
		UserDAOInterface userCategRightsDAO = new UserDAO();
		if (userCategRightsDAO.updateUserStatus(userStatus)) {
			String userRightsUpdate;
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				userRightsUpdate = objectMapper.writeValueAsString(userStatus);
				logger.info("The list of rights was successfully sent to the DB!");
				return new UserRightsResponse().getMessageJson(ResponseValues.SUCCESS);
			} catch (IOException e) {
				logger.error("Json error when trying to map the array of rights.");
				return new UserRightsResponse().getMessageJson(ResponseValues.UNKNOWN);
			}
		}
		else {
			return new UserRightsResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}
	
}
