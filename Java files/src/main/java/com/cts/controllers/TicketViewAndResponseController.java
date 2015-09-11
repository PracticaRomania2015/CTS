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
import com.cts.entities.User;

/**
 * Handle requests for view and response to tickets page.
 */
@Controller
public class TicketViewAndResponseController {

	private static Logger logger = Logger.getLogger(LoginController.class.getName());
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * POST method for the controller.
	 * 
	 * @param ticket
	 * @return ticket object with comments or error message.
	 */
	@RequestMapping(value = "/viewTicket", method = RequestMethod.POST)
	public @ResponseBody String viewTicket(@RequestBody Ticket ticket) {

		logger.info("Attempting to retrieve full details and comments for a ticket.");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.getFullTicket(ticket)){
			
			logger.info("Full ticket received successfully from db.");
		}
		
		String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {

			jsonMessage = objectMapper.writeValueAsString(ticket);
		} catch (IOException e) {

			logger.info("Json error when trying to map the ticket object.");
		}
		return jsonMessage;
	}

	/**
	 * POST method for the controller.
	 * 
	 * @param ticket
	 * @return ticket object with the new comment submitted or error message.
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public @ResponseBody String addComment(@RequestBody Ticket ticket) {

		logger.info("Attempting to add a comment for a ticket.");

		if (ticket == null || ticket.getComments().get(ticket.getComments().size() - 1).getComment() == null
				|| ticket.getComments().get(ticket.getComments().size() - 1).getComment() == null
				|| ticket.getComments().get(ticket.getComments().size() - 1).getComment().equals("")) {

			logger.info("Ticket comment cannot be null error.");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCOMMENTFIELD);
		}

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.addCommentToTicket(ticket)) {

			try {

				logger.info("The comment was added successfully!");
				return objectMapper.writeValueAsString(ticket);
			} catch (IOException e) {

				logger.info("Error while trying to map the json for ticket object.");
				return new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
			}
		} else {

			logger.info("Database error while trying to add a new comment!");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}
	
	/**
	 * POST method to assign admin to ticket.
	 * 
	 * @return success or error message if admin was assigned to ticket
	 */
	@RequestMapping(value = "/assignAdminToTicket", method = RequestMethod.POST)
	public @ResponseBody String assignTicket(@RequestBody Ticket ticket) {
		
		if (ticket == null) {

			logger.info("Ticket cannot be null error.");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}

		logger.info("Attempting to assign admin to ticket ...");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.assignTicket(ticket)) {
			
			logger.info("Ticket assigned to admin successfully!");
			return new TicketResponse().getMessageJson(ResponseValues.SUCCESS);
			
		} else {
			
			logger.info("Database error while trying to assign admin to ticket!");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);
			
		}
	}
	
	/**
	 * POST method to close ticket.
	 * 
	 * @return success or error message if ticket was closed
	 */
	@RequestMapping(value = "/closeTicket", method = RequestMethod.POST)
	public @ResponseBody String closeTicket(@RequestBody Ticket ticket) {

		if (ticket == null) {

			logger.info("Ticket cannot be null error.");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		logger.info("Attempting to close ticket ...");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.closeTicket(ticket)) {
			
			logger.info("Ticket closed successfully!");
			return new TicketResponse().getMessageJson(ResponseValues.SUCCESS);
			
		} else {
			
			logger.info("Database error while trying to close ticket!");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);
			
		}
	}
	
	/**
	 * POST method for the controller.
	 * 
	 * @param ticket
	 * @return ticket object with comments or error message.
	 */
	@RequestMapping(value = "/getAdminsForCategory", method = RequestMethod.POST)
	public @ResponseBody String getAdminsForCategory(@RequestBody Category category) {

		logger.info("Attempting to retrieve admins for category.");

		TicketDAOInterface ticketDAO = new TicketDAO();
		ArrayList<User> admins = ticketDAO.getAdminsForCategory(category);

		logger.info("Success to get admins for category from database ...");

		String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {
			jsonMessage = objectMapper.writeValueAsString(admins);
		} catch (IOException e) {
		}
		return jsonMessage;
	}
}
