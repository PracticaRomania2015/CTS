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
	 * POST method for view tickets controller.
	 * 
	 * @return list of tickets or error message.
	 */
	@RequestMapping(value = "/rootAddCategory", method = RequestMethod.POST)
	public @ResponseBody String addCateg(@RequestBody Category category) {

		logger.info("Attempting to add a category ...");

		CategoryDAOInterface categoryDAO = new CategoryDAO();
		if (categoryDAO.addCategory(category)) {

			return new CategoryResponse().getMessageJson(ResponseValues.SUCCESS);
		} else {

			return new CategoryResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
	}

	/**
	 * POST method for view tickets controller.
	 * 
	 * @return list of tickets or error message.
	 */
	@RequestMapping(value = "/rootRemoveCategory", method = RequestMethod.POST)
	public @ResponseBody String removeCateg(@RequestBody Category category) {

		logger.info("Attempting to delete a category ...");

		CategoryDAOInterface categoryDAO = new CategoryDAO();
		if (categoryDAO.deleteCategory(category)) {

			return new CategoryResponse().getMessageJson(ResponseValues.SUCCESS);
		} else {

			return new CategoryResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
	}
}