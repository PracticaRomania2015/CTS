package com.cts.controllers;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.ResponseValues;
import com.cts.communication.TicketResponse;
import com.cts.dao.TicketDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.entities.Ticket;

/**
 * Handle requests for view and response to tickets page.
 */
@Controller
@Scope("session")
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
	@ResponseBody
	public String viewTicketComments(@RequestBody Ticket ticket) {

		logger.info("Attempting to retrieve full details and comments for a ticket.");

		TicketDAOInterface ticketDAO = new TicketDAO();
		ticket.setComments(ticketDAO.getTicketComments(ticket));
		String jsonMessage = "json error";
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
	@ResponseBody
	public String addComment(@RequestBody Ticket ticket) {

		logger.info("Attempting to add a comment for a ticket.");

		if (ticket == null || ticket.getComments().get(ticket.getComments().size() - 1).getComment() == null
				|| ticket.getComments().get(ticket.getComments().size() - 1).getComment() == null
				|| ticket.getComments().get(ticket.getComments().size() - 1).getComment().equals("")) {

			logger.info("Ticket comment cannot be null error.");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYDESCRIPTIONFIELD);
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
	 * @return ticket assigned success or error message
	 */
	@RequestMapping(value = "/assignTicket", method = RequestMethod.POST)
	@ResponseBody
	public String assignTicket(@RequestBody Ticket ticketToAssign) {

		logger.info("Attempting to assign user to ticket ...");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.addCommentToTicket(ticketToAssign)) {
			
			logger.info("Ticket assigned to user successfully");
			return new TicketResponse().getMessageJson(ResponseValues.SUCCESS);
			
		} else {
			
			logger.info("Database error while trying to assign user to ticket!");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
			
		}
	}
}