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
import com.cts.communication.TicketResponse;
import com.cts.dao.CategoryDAO;
import com.cts.dao.CategoryDAOInterface;
import com.cts.dao.TicketDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.Ticket;

/**
 * Handle requests for the submit ticket page.
 */
@Controller
public class SubmitTicketController {

	private static Logger logger = Logger.getLogger(SubmitTicketController.class.getName());
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * POST method to get categories
	 * 
	 * @return all categories or null
	 */
	@RequestMapping(value = "/getCategories", method = RequestMethod.POST)
	public @ResponseBody String getCategories() {

		logger.info("DEBUG: Attempting to get categories from database.");

		CategoryDAOInterface categoryDAO = new CategoryDAO();
		ArrayList<Category> categories = categoryDAO.getCategories();

		logger.info("INFO: Success to get categories from database ...");

		String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {
			jsonMessage = objectMapper.writeValueAsString(categories);
		} catch (IOException e) {
		}
		return jsonMessage;
	}

	/**
	 * POST method to get subcategories for a category
	 * 
	 * @return subcategories for a category or null
	 */
	@RequestMapping(value = "/getSubCategories", method = RequestMethod.POST)
	public @ResponseBody String getSubcategories(@RequestBody Category category) {

		logger.info("DEBUG: Attempting to get subcategories for a category from database.");

		CategoryDAOInterface categoryDAO = new CategoryDAO();
		ArrayList<Category> subcategories = categoryDAO.getSubcategories(category);

		logger.info("INFO: Success to get subcategories for a category from database.");

		String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {
			jsonMessage = objectMapper.writeValueAsString(subcategories);
		} catch (IOException e) {
		}
		return jsonMessage;
	}

	/**
	 * Post method for the submit ticket controller.
	 * 
	 * @param ticket
	 * @return submit ticket page or ticket submitted successfully page.
	 */
	@RequestMapping(value = "/submitTicket", method = RequestMethod.POST)
	public @ResponseBody String submitTicket(@RequestBody Ticket ticket) {

		logger.info("DEBUG: Attempting a ticket submission.");

		// Ticket subject validation
		if (ticket.getSubject() == null){
			
			logger.info("ERROR: Ticket subject is null!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYSUBJECT);
		}
		if (ticket.getSubject().equals("")) {
			
			logger.info("ERROR: Ticket subject is empty!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYSUBJECT);
		}
		
		// Ticket category validation
		if (ticket.getCategory() == null){
			
			logger.info("ERROR: Ticket category is null!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCATEGORY);
		}
		if (ticket.getCategory().equals("")) {
			
			logger.info("ERROR: Ticket category is empty!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCATEGORY);
		}

		// Ticket description validation
		if (ticket.getComments().get(0).getComment() == null){
			
			logger.info("ERROR: Ticket description is null!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYDESCRIPTION);
		}
		if (ticket.getComments().get(0).getComment().equals("")) {
			
			logger.info("ERROR: Ticket description is empty!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYDESCRIPTION);
		}

		// Ticket creation.
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.createTicket(ticket)) {

			logger.info("INFO: Ticket submitted succesfully!");
			String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
			try {
				jsonMessage = objectMapper.writeValueAsString(ticket);
			} catch (IOException e) {
			}
			return jsonMessage;
		} else {

			logger.info("ERROR: Database error when trying to create a new ticket.");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}
}
