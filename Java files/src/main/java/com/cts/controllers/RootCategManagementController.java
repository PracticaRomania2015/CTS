package com.cts.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.CategoryResponse;
import com.cts.communication.ResponseValues;
import com.cts.dao.CategoryDAO;
import com.cts.dao.CategoryDAOInterface;
import com.cts.entities.Category;

/**
 * Handle request for view tickets.
 */
@Controller
public class RootCategManagementController {

	private static Logger logger = Logger.getLogger(RootCategManagementController.class.getName());

	/**
	 * Add category
	 * 
	 * @param category The new category to be added.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/rootAddCategory", method = RequestMethod.POST)
	public @ResponseBody String addCateg(@RequestBody Category category) {

		logger.debug("Attempting to add a category.");
		
		// Category name validation
		if (category.getCategoryName() == null){
			
			logger.error("Category name is null.");
			return new CategoryResponse().getMessageJson(ResponseValues.EMPTYCATEGORYNAME);
		}
		if (category.getCategoryName().equals("")){
			
			logger.error("Category name is empty.");
			return new CategoryResponse().getMessageJson(ResponseValues.EMPTYCATEGORYNAME);
		}
		
		// Adding category
		CategoryDAOInterface categoryDAO = new CategoryDAO();
		if (categoryDAO.addCategory(category)) {

			return new CategoryResponse().getMessageJson(ResponseValues.SUCCESS);
		} else {

			return new CategoryResponse().getMessageJson(ResponseValues.UNKNOWN);
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

		logger.debug("Attempting to delete a category.");

		// Category ID validation
		if (Integer.valueOf(category.getCategoryId()) == null) {
			
			logger.error("Category ID is null.");
			return new CategoryResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		CategoryDAOInterface categoryDAO = new CategoryDAO();
		if (categoryDAO.deleteCategory(category)) {

			return new CategoryResponse().getMessageJson(ResponseValues.SUCCESS);
		} else {

			return new CategoryResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
	}
}