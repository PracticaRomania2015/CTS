package com.cts.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.ResponseValues;
import com.cts.communication.UtilsResponse;
import com.cts.dao.CategoryDAO;
import com.cts.dao.CategoryDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.User;

/**
 * Handles requests for general functionality.
 */

@Controller
public class UtilsController {

	private static Logger logger = Logger.getLogger(TicketsController.class.getName());
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Get all categories
	 * 
	 * @return All categories in JSON format.
	 */
	@RequestMapping(value = "/getCategories", method = RequestMethod.POST)
	public @ResponseBody String getCategories() {

		logger.debug("Attempting to get categories from database.");

		CategoryDAOInterface categoryDAO = new CategoryDAO();
		ArrayList<Category> categories = categoryDAO.getCategories();

		logger.info("Success to get categories from database.");

		String jsonMessage = new UtilsResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {
			jsonMessage = objectMapper.writeValueAsString(categories);
		} catch (IOException e) {
		}
		return jsonMessage;
	}

	/**
	 * Get subcategories of category provided 
	 * 
	 * @param category Category from which to get subcategories
	 * @return Subcategories from category provided
	 */
	@RequestMapping(value = "/getSubCategories", method = RequestMethod.POST)
	public @ResponseBody String getSubcategories(@RequestBody Category category) {

		logger.debug("Attempting to get subcategories for a category from database.");

		// Parameter validation
		if (category == null){
			
			logger.error("Category is null!");
			return new UtilsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// Category ID validation
		if (Integer.valueOf(category.getCategoryId()) == null){
			
			logger.error("Category ID if null.");
			return new UtilsResponse().getMessageJson(ResponseValues.ERROR);
		}
			
		if (Integer.valueOf(category.getCategoryId()).equals("")){
			
			logger.error("Category ID if empty.");
			return new UtilsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		CategoryDAOInterface categoryDAO = new CategoryDAO();
		ArrayList<Category> subcategories = categoryDAO.getSubcategories(category);

		logger.info("Success to get subcategories for a category from database.");

		String jsonMessage = new UtilsResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {
			jsonMessage = objectMapper.writeValueAsString(subcategories);
		} catch (IOException e) {
		}
		return jsonMessage;
	}
	
	/**
	 * Get admins for a specified category
	 * 
	 * @param category Category from which to get admins.
	 * @return List of admins for specified category in JSON format.
	 */
	@RequestMapping(value = "/getAdminsForCategory", method = RequestMethod.POST)
	public @ResponseBody String getAdminsForCategory(@RequestBody Category category) {

		logger.debug("Attempting to retrieve admins for category.");

		// TicketId Validation
		if (category.getCategoryId() == 0){
			
			logger.error("Invalid CategoryId");
			return new UtilsResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// Getting admins for category
		UserDAOInterface userDAO = new UserDAO();
		ArrayList<User> admins = userDAO.getAdminsForCategory(category);

		String jsonMessage = new UtilsResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {
			
			logger.info("Received admins from database!");
			jsonMessage = objectMapper.writeValueAsString(admins);
		} catch (IOException e) {
			
			logger.error("Error while trying to map the json for ticket object.");
			return new UtilsResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
		return jsonMessage;
	}
}