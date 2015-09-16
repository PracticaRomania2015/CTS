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
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.Ticket;
import com.cts.entities.User;

/**
 * Handle requests for view and response to tickets page.
 */
@Controller
public class TicketViewAndResponseController {

	private static Logger logger = Logger.getLogger(TicketViewAndResponseController.class.getName());
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Get details for a ticket
	 * 
	 * @param ticket
	 * @return ticket object with comments or error message.
	 */
	@RequestMapping(value = "/viewTicket", method = RequestMethod.POST)
	public @ResponseBody String viewTicket(@RequestBody Ticket ticket) {

		logger.info("DEBUG: Attempting to retrieve full details and comments for a ticket.");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.getFullTicket(ticket)) {
			logger.info("INFO: Full ticket received successfully from db.");
		}

		String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {

			jsonMessage = objectMapper.writeValueAsString(ticket);
		} catch (IOException e) {

			logger.info("ERROR: Json error when trying to map the ticket object.");
		}
		return jsonMessage;
	}

	/**
	 * Add comment to a specified ticket
	 * 
	 * @param ticket
	 * @return ticket object with the new comment submitted or error message.
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public @ResponseBody String addComment(@RequestBody Ticket ticket) {

		logger.info("DEBUG: Attempting to add a comment for a ticket.");

		// New comment validation
		if (ticket.getComments().get(0).getComment() == null){
			
			logger.info("ERROR: Last ticket comment is null!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCOMMENT);
		}
		if (ticket.getComments().get(0).getComment().equals("")) {
			
			logger.info("ERROR: Last ticket comment is empty!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCOMMENT);
		}

		// Adding comment to ticket
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.addCommentToTicket(ticket)) {

			try {

				logger.info("INFO: The comment was added successfully!");
				return objectMapper.writeValueAsString(ticket);
			} catch (IOException e) {

				logger.info("ERROR: Error while trying to map the json for ticket object.");
				return new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
			}
		} else {

			logger.info("ERROR: Database error while trying to add a new comment!");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}

	/**
	 * Assign user to a specified ticket
	 * 
	 * @return success or error message if user was assigned to ticket
	 */
	@RequestMapping(value = "/assignUserToTicket", method = RequestMethod.POST)
	public @ResponseBody String assignTicket(@RequestBody Ticket ticket) {

		logger.info("DEBUG: Attempting to assign an user to a ticket.");
		
		// UserId Validation
		if (ticket.getAssignedToUser().getUserId() == 0){
			
			logger.info("ERROR: Invalid UserId");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// TicketId Validation
		if (ticket.getTicketId() == 0){
			
			logger.info("ERROR: Invalid TicketId");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}

		// Assigning user to ticket
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.assignTicket(ticket)) {

			logger.info("INFO: Ticket assigned to user successfully!");
			return new TicketResponse().getMessageJson(ResponseValues.SUCCESS);

		} else {

			logger.info("ERROR: Database error while trying to assign user to ticket!");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);

		}
	}

	/**
	 * Change specified ticket status to closed
	 * 
	 * @return success or error message if ticket was closed
	 */
	@RequestMapping(value = "/closeTicket", method = RequestMethod.POST)
	public @ResponseBody String closeTicket(@RequestBody Ticket ticket) {

		logger.info("DEBUG: Attempting to change ticket status to closed.");
		
		// TicketId Validation
		if (ticket.getTicketId() == 0){
			
			logger.info("ERROR: Invalid TicketId");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}

		// Changing ticket status to closed
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.closeTicket(ticket)) {

			logger.info("INFO: Ticket closed successfully!");
			return new TicketResponse().getMessageJson(ResponseValues.SUCCESS);

		} else {

			logger.info("ERROR: Database error while trying to close ticket!");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);

		}
	}

	/**
	 * Get admins for a specified category
	 * 
	 * @param ticket
	 * @return ticket object with comments or error message.
	 */
	@RequestMapping(value = "/getAdminsForCategory", method = RequestMethod.POST)
	public @ResponseBody String getAdminsForCategory(@RequestBody Category category) {

		logger.info("DEBUG: Attempting to retrieve admins for category.");

		// TicketId Validation
		if (category.getCategoryId() == 0){
			
			logger.info("ERROR: Invalid CategoryId");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// Getting admins for category
		UserDAOInterface userDAO = new UserDAO();
		ArrayList<User> admins = userDAO.getAdminsForCategory(category);

		String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {
			
			logger.info("INFO: Received admins from database!");
			jsonMessage = objectMapper.writeValueAsString(admins);
		} catch (IOException e) {
			
			logger.info("ERROR: Error while trying to map the json for ticket object.");
			return new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
		return jsonMessage;
	}
}
