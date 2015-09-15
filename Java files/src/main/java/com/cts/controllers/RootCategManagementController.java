package com.cts.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.ResponseValues;
import com.cts.entities.Category;

/**
 * Handle request for view tickets.
 */
@Controller
public class RootCategManagementController {

	private static Logger logger = Logger.getLogger(RootAddCateg.class.getName());

	/**
	 * POST method for view tickets controller.
	 * 
	 * @return list of tickets or error message.
	 */
	@RequestMapping(value = "/rootAddCategory", method = RequestMethod.POST)
	public @ResponseBody String addCateg(@RequestBody Category category) {

		logger.info("Attempting to add a category ...");

		CategoryDAOInteface categDAO = new CategoryDAO();
		if (catedDAO.addCategory(category))
		{
			return new CategoryResponse().getMessageJson(ResponseValues.SUCCESS);
		}
		else
		{
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

		CategoryDAOInteface categDAO = new CategoryDAO();
		if (catedDAO.removeCategory(category))
		{
			return new CategoryResponse().getMessageJson(ResponseValues.SUCCESS);
		}
		else
		{
			return new CategoryResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
	}
	
	/**
	 * POST method for view tickets controller.
	 * 
	 * @return list of tickets or error message.
	 */
	@RequestMapping(value = "/rootAddSubcategory", method = RequestMethod.POST)
	public @ResponseBody String addSubcateg(@RequestBody Category category) {

		logger.info("Attempting to add a subcategory ...");

		CategoryDAOInteface categDAO = new CategoryDAO();
		if (catedDAO.addSubcategory(category))
		{
			return new CategoryResponse().getMessageJson(ResponseValues.SUCCESS);
		}
		else
		{
			return new CategoryResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
	}
	
}