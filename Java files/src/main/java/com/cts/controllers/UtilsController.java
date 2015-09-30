package com.cts.controllers;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.ResponseMessage;
import com.cts.communication.ResponseValues;
import com.cts.dao.CategoryDAO;
import com.cts.dao.CategoryDAOInterface;
import com.cts.dao.PriorityDAO;
import com.cts.dao.PriorityDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.Priority;
import com.cts.entities.User;

/**
 * Handles requests for general functionality.
 */
@Controller
public class UtilsController {

	private static Logger logger = Logger.getLogger(TicketsController.class.getName());

	/**
	 * Get all categories
	 * 
	 * @return All categories in JSON format.
	 */
	// TODO: CHECK FOR 0 CATEGORIES
	@RequestMapping(value = "/getCategories", method = RequestMethod.POST)
	public @ResponseBody String getCategories() {

		logger.debug("Attempting to retrieve categories.");

		CategoryDAOInterface categoryDAO = new CategoryDAO();
		ArrayList<Category> categories = categoryDAO.getCategories();

		logger.info("Categories received successfully!");
		return new ResponseMessage(categories).getMessageJSON(ResponseValues.SUCCESS);
	}

	/**
	 * Get subcategories of category provided
	 * 
	 * @param category
	 *            Category from which to get subcategories
	 * @return Subcategories from category provided
	 */
	// TODO: CHECK FOR 0 SUBCATEGORIES
	@RequestMapping(value = "/getSubcategories", method = RequestMethod.POST)
	public @ResponseBody String getSubcategories(@RequestBody Category category) {

		logger.debug("Attempting to get subcategories for a category.");

		CategoryDAOInterface categoryDAO = new CategoryDAO();
		ArrayList<Category> subcategories = categoryDAO.getSubcategories(category);

		if (subcategories != null) {

			logger.info("Subcategories received successfully!");
			return new ResponseMessage(subcategories).getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.info("Subcategories could not be retrieved!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}

	}

	/**
	 * Get admins for a specified category
	 * 
	 * @param category
	 *            Category from which to get admins.
	 * @return List of admins for specified category in JSON format.
	 */
	// TODO: CHECK FOR 0 ADMINS
	@RequestMapping(value = "/getAdminsForCategory", method = RequestMethod.POST)
	public @ResponseBody String getAdminsForCategory(@RequestBody Category category) {

		logger.debug("Attempting to retrieve admins for category.");

		UserDAOInterface userDAO = new UserDAO();
		ArrayList<User> admins = userDAO.getAdminsForCategory(category);

		if (admins != null) {

			logger.info("Admins for the provided category received successfully!");
			return new ResponseMessage(admins).getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.info("Admins for the provided category could not be retrieved!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}
	}

	@RequestMapping(value = "/getPriorities", method = RequestMethod.POST)
	public @ResponseBody String getPriorities() {

		logger.debug("Attempting to retrieve priorities.");

		PriorityDAOInterface priorityDAO = new PriorityDAO();
		ArrayList<Priority> priorities = priorityDAO.getPriorities();

		if (priorities != null) {

			logger.info("Priorities received successfully!");
			return new ResponseMessage(priorities).getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.info("Priorities could not be retrieved!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}
	}
}