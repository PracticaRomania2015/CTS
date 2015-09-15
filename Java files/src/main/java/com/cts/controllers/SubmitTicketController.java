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
import com.cts.dao.TicketDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.Ticket;

/**
 * Handle requests for submit ticket page.
 */
@Controller
public class SubmitTicketController {

	private static Logger logger = Logger.getLogger(SubmitTicketController.class.getName());
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * GET method to get categories for the create new ticket page.
	 * 
	 * @return all categories or null
	 */
	@RequestMapping(value = "/getCategories", method = RequestMethod.POST)
	public @ResponseBody String getCategories() {

		logger.info("Attempting to get categories from database ...");

		TicketDAOInterface ticketDAO = new TicketDAO();
		ArrayList<Category> categories = ticketDAO.getCategories();

		logger.info("Success to get categories from database ...");

		String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {
			jsonMessage = objectMapper.writeValueAsString(categories);
		} catch (IOException e) {
		}
		return jsonMessage;
	}

	/**
	 * GET method to get subcategories for a category
	 * 
	 * @return subcategories for a category or null
	 */
	@RequestMapping(value = "/getSubCategories", method = RequestMethod.POST)
	public @ResponseBody String getSubcategories(@RequestBody Category category) {

		logger.info("Attempting to get subcategories for a category from database ...");

		TicketDAOInterface ticketDAO = new TicketDAO();
		ArrayList<Category> subcategories = ticketDAO.getSubcategories(category);

		logger.info("Success to get subcategories for a category from database ...");

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

		logger.info("Attempting a ticket submission ...");

		// Check if the ticket subject is null or empty.
		if (ticket.getSubject() == null || ticket.getSubject().equals("")) {

			logger.info("Ticket subject is null or empty.");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYSUBJECTFIELD);
		}

		// Check if the ticket category is null or empty.
		if (ticket.getCategory().getCategoryId() == 0) {

			logger.info("You must select a category for the ticket!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETNOCATEGORYSELECTED);
		}

		// Check if the ticket description is null or empty.
		if (ticket.getComments().get(0).getComment() == null || ticket.getComments().get(0).getComment().equals("")) {

			logger.info("Ticket description is null or empty.");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYDESCRIPTIONFIELD);
		}

		// Create a new ticket.
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.createTicket(ticket)) {

			logger.info("Ticket submitted succesfully!");
			String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
			try {
				jsonMessage = objectMapper.writeValueAsString(ticket);
			} catch (IOException e) {
			}
			return jsonMessage;
		} else {

			logger.info("Database error when trying to create a new ticket.");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}
}
