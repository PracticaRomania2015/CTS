package com.cts.controllers;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.CategoryResponse;
import com.cts.communication.ResponseValues;
import com.cts.communication.UserRightsResponse;
import com.cts.dao.CategoryDAO;
import com.cts.dao.CategoryDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.User;
import com.cts.entities.UserStatus;
import com.cts.entities.ViewUsersRequest;

/**
 * Handle request for view tickets.
 */
@Controller
public class RootManagementController {

	private static Logger logger = Logger.getLogger(RootManagementController.class.getName());

	/**
	 * Add category
	 * 
	 * @param category The new category to be added.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/rootAddCategory", method = RequestMethod.POST)
	public @ResponseBody String addCateg(@RequestBody Category category) {

		logger.debug("Attempting to add a category/subcategory.");
		
		// Category name validation
		if (category.getCategoryName() == null){
			
			logger.error("Category/Subcategory name is null.");
			return new CategoryResponse().getMessageJSON(ResponseValues.CATEGORYEMPTYNAME);
		}
		if (category.getCategoryName().equals("")){
			
			logger.error("Category/Subcategory name is empty.");
			return new CategoryResponse().getMessageJSON(ResponseValues.CATEGORYEMPTYNAME);
		}
		
		// Adding category
		CategoryDAOInterface categoryDAO = new CategoryDAO();
		if (categoryDAO.addCategory(category)) {

			logger.info("Category/Subcategory added successfully!");
			return new CategoryResponse().getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.warn("Category/Subcategory could not be added!");
			return new CategoryResponse().getMessageJSON(ResponseValues.UNKNOWN);
		}
	}

	/**
	 * Remove category
	 * 
	 * @param category The Category to be removed.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/rootRemoveCategory", method = RequestMethod.POST)
	public @ResponseBody String removeCateg(@RequestBody Category category) {

		logger.debug("Attempting to delete a category/subcategory.");
		
		if (Integer.valueOf(category.getCategoryId()).equals("")){
			
			logger.error("Category/Subcategory ID is empty.");
			return new CategoryResponse().getMessageJSON(ResponseValues.CATEGORYEMPTYID);
		}
		
		// Removing category
		CategoryDAOInterface categoryDAO = new CategoryDAO();
		if (categoryDAO.deleteCategory(category)) {

			logger.info("Category/Subcategory removed successfully!");
			return new CategoryResponse().getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.warn("Category/Subcategory could not be removed!");
			return new CategoryResponse().getMessageJSON(ResponseValues.DBERROR);
		}
	}
	
	/**
	 * Get particular list of users
	 * 
	 * @param viewUserRequest ViewUserRequest with details regarding which users to receive. 
	 * @return List of users in JSON format.
	 */
	@RequestMapping(value = "/getUsers", method = RequestMethod.POST)
	public @ResponseBody String getUsers(@RequestBody ViewUsersRequest viewUserRequest) {

		logger.debug("Attempting to retrieve a particular list of users.");
		
		UserDAOInterface userDAO = new UserDAO();
		StringBuilder totalNumberOfPages = new StringBuilder();
		ArrayList<User> users = userDAO.getUsers(viewUserRequest, totalNumberOfPages);
		
		if (totalNumberOfPages.toString().equals("")){
			
			totalNumberOfPages.append("1");
		}

		ArrayList<Object> output = new ArrayList<Object>();
		output.add(totalNumberOfPages);
		output.add(users);
		logger.info("Requested users received successfully!");
		return new UserRightsResponse(output).getMessageJSON(ResponseValues.SUCCESS);
	}
	
	/**
	 * Get rights of provided user
	 * 
	 * @param userStatus User to get the rights for.
	 * @return UserStatus with User ID and it's rights.
	 */
	@RequestMapping(value = "/getUserRights", method = RequestMethod.POST)
	public @ResponseBody String getUserRights(@RequestBody UserStatus userStatus) {

		logger.debug("Attempting to get a user's rights.");
		
		// Is Sys Admin validation
		if (userStatus.isSysAdmin() != (true && false)) {
			
			logger.error("isSysAdmin is not set!");
			return new UserRightsResponse().getMessageJSON(ResponseValues.ROOTMANAGEMENTEMPTYSYSADMIN);
		}
		
		// Getting user's rights
		CategoryDAOInterface userCategRightsDAO = new CategoryDAO();
		if (userCategRightsDAO.viewCategoriesRightsForUser(userStatus)) {

			logger.info("User's rights received successfully!");
			return new UserRightsResponse(userStatus).getMessageJSON(ResponseValues.SUCCESS);
		}
		else {
			
			logger.error("User's rights could not be retrieved!");
			return new UserRightsResponse().getMessageJSON(ResponseValues.DBERROR);
		}
	}
	
	/**
	 * Change rights of provided user
	 * 
	 * @param userStatus User to have the rights changed.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/setUserRights", method = RequestMethod.POST)
	public @ResponseBody String setUserRights(@RequestBody UserStatus userStatus) {

		logger.debug("Attempting to set a user's rights.");
		
		UserDAOInterface userCategRightsDAO = new UserDAO();
		if (userCategRightsDAO.updateUserStatus(userStatus)) {
			
			logger.info("User's rights updated successfully!");
			return new UserRightsResponse().getMessageJSON(ResponseValues.SUCCESS);
		}
		else {
			
			logger.error("User's rights could not be updated!");
			return new UserRightsResponse().getMessageJSON(ResponseValues.DBERROR);
		}
	}
}