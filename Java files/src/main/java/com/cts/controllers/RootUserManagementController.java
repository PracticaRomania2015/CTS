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
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Get particular list of users
	 * 
	 * @param viewUserRequest ViewUserRequest with details regarding which users to receive. 
	 * @return List of users in JSON format.
	 */
	@RequestMapping(value = "/viewUsers", method = RequestMethod.POST)
	public @ResponseBody String viewUsers(@RequestBody ViewUsersRequest viewUserRequest) {

		logger.debug("Attempting to retrieve a particular list of users.");

		// Parameter validation
		if (viewUserRequest == null){
			
			logger.error("ViewUserRequest is null!");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// requestedPageNumber validation
		if (Integer.valueOf(viewUserRequest.getRequestedPageNumber()) == null) {
			
			logger.error("Requested Page Number is null.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		if (Integer.valueOf(viewUserRequest.getRequestedPageNumber()).equals("")){
			
			logger.error("Requested Page Number is empty.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// usersPerPage validation
		if (Integer.valueOf(viewUserRequest.getUsersPerPage()) == null) {
			
			logger.error("Users Per Page is null.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		if (Integer.valueOf(viewUserRequest.getUsersPerPage()).equals("")){
			
			logger.error("Users Per Page is empty.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// textToSearch validation
		if (viewUserRequest.getTextToSearch() == null){
			
			logger.error("Text To Search is null.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		if (viewUserRequest.getTextToSearch().equals("")){
			
			logger.error("Text To Search is empty.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// searchType validation
		if (viewUserRequest.getSearchType() == null){
			
			logger.error("Search Type is null.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		if (viewUserRequest.getSearchType().equals("")){
			
			logger.error("Search Type is empty.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// sortType validation
		if (viewUserRequest.getSortType() == null){
			
			logger.error("Sort Type is null.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		if (viewUserRequest.getSortType().equals("")){
			
			logger.error("Sort Type is empty.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}

		// Getting users
		UserDAOInterface userDAO = new UserDAO();
		StringBuilder totalNumberOfPages = new StringBuilder();
		ArrayList<User> users = userDAO.getUsers(viewUserRequest, totalNumberOfPages);
		String usersJson;
		try {
			
			usersJson = objectMapper.writeValueAsString(users);
			logger.info("Successfully retrieved the list of users!");
			return "{\"totalNumberOfPages\":" + totalNumberOfPages + ",\"users\":" + usersJson + "}";
		} catch (IOException e) {

			logger.error("JSON error when trying to map the array of users.");
			return new UserRightsResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
	}
	
	/**
	 * Get rights of provided user
	 * 
	 * @param userStatus User to get the rights for.
	 * @return UserStatus with User ID and it's rights.
	 */
	@RequestMapping(value = "/getUserAdminStatus", method = RequestMethod.POST)
	public @ResponseBody String getUserAdminStatus(@RequestBody UserStatus userStatus) {

		logger.debug("Attempting to get user rights.");
		
		// Parameter validation
		if (userStatus == null){
			
			logger.error("UserStatus is null!");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// User ID validation
		if (Integer.valueOf(userStatus.getUserId()) == null) {
			
			logger.error("User ID is null.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		if (Integer.valueOf(userStatus.getUserId()).equals("")){
			
			logger.error("User ID is empty.");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// Is Sys Admin validation
		if (userStatus.isSysAdmin() != (true && false)) {
			
			logger.error("isSysAdmin is not set!");
			return new UserRightsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		CategoryDAOInterface userCategRightsDAO = new CategoryDAO();
		if (userCategRightsDAO.viewCategoriesRightsForUser(userStatus)) {
			String userRightsStatus;
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				
				userRightsStatus = objectMapper.writeValueAsString(userStatus);
				logger.info("The list of rights was successfully retrieved!");
				return "{\"rights\":" + userRightsStatus + "}";
			} catch (IOException e) {
				
				logger.error("JSON error when trying to map the array of rights.");
				return new UserRightsResponse().getMessageJson(ResponseValues.UNKNOWN);
			}
		}
		else {
			
			logger.error("Could not retrieve categories from database!");
			return new UserRightsResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}
	
	/**
	 * Change user rights
	 * 
	 * @param userStatus User to have rights changed.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/setUserAdminStatus", method = RequestMethod.POST)
	public @ResponseBody String setUserAdminStatus(@RequestBody UserStatus userStatus) {

		logger.debug("Attempting to set user rights ...");
		
		UserDAOInterface userCategRightsDAO = new UserDAO();
		if (userCategRightsDAO.updateUserStatus(userStatus)) {
			
			logger.info("The list of rights was successfully sent to the DB!");
			return new UserRightsResponse().getMessageJson(ResponseValues.SUCCESS);
		}
		else {
			
			logger.error("Could not save new user status to database!");
			return new UserRightsResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}
	
}
