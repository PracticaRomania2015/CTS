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
import com.cts.dao.TicketDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.Ticket;
import com.cts.entities.User;
import com.cts.entities.ViewTicketsRequest;
import com.cts.utils.SendEmail;

/**
 * Handles requests for the tickets.
 */
@Controller
public class TicketsController {

	private static Logger logger = Logger.getLogger(TicketsController.class.getName());

	/**
	 * Get details of provided ticket
	 * 
	 * @param ticket
	 *            Ticket to get details for.
	 * @return Complete ticket in JSON format.
	 */
	// TODO:
	// CHANGED FROM: /viewTicket
	@RequestMapping(value = "/getTicket", method = RequestMethod.POST)
	public @ResponseBody String getTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting to retrieve full details and comments for a ticket.");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.getFullTicket(ticket)) {

			logger.info("Full ticket received successfully!");
			return new ResponseMessage(ticket).getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.warn("Full ticket could not be retrieved!");
			return new ResponseMessage().getMessageJSON(ResponseValues.UNKNOWN);
		}
	}

	/**
	 * Get particular list of tickets
	 * 
	 * @param viewTicketsRequest
	 *            ViewTicketsRequest with details regarding which tickets to
	 *            receive.
	 * @return List of tickets in JSON format.
	 */
	// TODO:
	// CHANGED FROM: /viewTickets
	@RequestMapping(value = "/getTickets", method = RequestMethod.POST)
	public @ResponseBody String getTickets(@RequestBody ViewTicketsRequest viewTicketsRequest) {

		logger.debug("Attempting to retrieve a particular list of tickets.");

		// Getting tickets
		TicketDAOInterface ticketDAO = new TicketDAO();
		StringBuilder totalNumberOfPages = new StringBuilder();
		ArrayList<Ticket> tickets = ticketDAO.getTickets(viewTicketsRequest, totalNumberOfPages);

		ArrayList<Object> output = new ArrayList<Object>();
		output.add(totalNumberOfPages);
		output.add(tickets);
		logger.info("Requested tickets received successfully!");
		return new ResponseMessage(output).getMessageJSON(ResponseValues.SUCCESS);

	}

	/**
	 * Submit ticket
	 * 
	 * @param ticket
	 *            Ticket to be submitted.
	 * @return Ticket with complete details in JSON format.
	 */
	@RequestMapping(value = "/submitTicket", method = RequestMethod.POST)
	public @ResponseBody String submitTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting to submit a ticket.");

		// Ticket subject validation
		if (ticket.getSubject() == null) {

			logger.error("Ticket subject is null!");
			return new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYSUBJECT);
		}
		if (ticket.getSubject().equals("")) {

			logger.error("Ticket subject is empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYSUBJECT);
		}

		// Ticket category validation
		if (ticket.getCategory() == null) {

			logger.error("Ticket category is null!");
			return new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYCATEGORY);
		}

		if (ticket.getCategory().getCategoryId() == 0) {

			logger.error("Ticket category is invalid!");
			return new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYCATEGORY);
		}

		// Ticket description validation
		if (ticket.getComments().get(0).getComment() == null) {

			logger.error("Ticket description is null!");
			return new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYDESCRIPTION);
		}
		if (ticket.getComments().get(0).getComment().equals("")) {

			logger.error("Ticket description is empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYDESCRIPTION);
		}

		// Ticket creation.
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.createTicket(ticket)) {

			logger.info("Ticket submitted succesfully!");
			return new ResponseMessage(ticket).getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.info("Ticket could not be submitted!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}
	}

	/**
	 * Add comment to specified ticket
	 * 
	 * @param ticket
	 *            Ticket to which to add comment to.
	 * @return Ticket with added comment details from database in JSON format.
	 */
	// TODO:
	// CHANGED FROM: /addComment
	@RequestMapping(value = "/addCommentToTicket", method = RequestMethod.POST)
	public @ResponseBody String addCommentToTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting to add a comment to a ticket.");

		// New comment validation
		if (ticket.getComments().get(0).getComment() == null) {

			logger.error("Ticket comment is null!");
			return new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYCOMMENT);
		}
		if (ticket.getComments().get(0).getComment().equals("")) {

			logger.error("Ticket comment is empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYCOMMENT);
		}

		// Adding comment to ticket
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.addCommentToTicket(ticket)) {

			User user = null;
			UserDAOInterface userDAO = new UserDAO();
			user = userDAO.getTicketUser(ticket);
			if (user != null) {

				logger.info(
						"User who created the ticket retrieved successfully! Trying to sent the email for notification!");
				if (user.getUserId() != ticket.getComments().get(ticket.getComments().size() - 1).getUser()
						.getUserId()) {

					String subject = "CTS - Notification Manager";
					String msg = "Hello " + user.getFirstName() + " " + user.getLastName()
							+ ",\n\nAn admin has responded to your ticket #" + ticket.getTicketId()
							+ ".\n\nRegards,\nCTS team\n\n*** Please do not respond to this e-mail as it is an automated message. Replies will not be received.***";

					if (SendEmail.sendEmail(user.getEmail(), subject, msg)) {

						logger.info("An email was sent to notify the ticket user that a new comment was added.");
					} else {

						logger.info("Failed to send an email to notify the ticket user that a new comment was added.");
					}
				} else {

					logger.info("The comment was posted by the user who created the ticket. No email will be sent!");
				}
			} else {

				logger.info("Cannot retrive the user who submitted the ticket!");
			}
			logger.info("Comment submitted successfully!");
			return new ResponseMessage(ticket).getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.error("Comment could not be submitted!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}
	}

	/**
	 * Assign admin to a specified ticket
	 * 
	 * @param ticket
	 *            Ticket to which to assign the admin
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/assignAdminToTicket", method = RequestMethod.POST)
	public @ResponseBody String assignAdminToTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting to assign an admin to a ticket.");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.assignTicket(ticket)) {

			logger.info("Admin assigned to ticket successfully!");
			return new ResponseMessage().getMessageJSON(ResponseValues.SUCCESS);

		} else {

			logger.error("Admin could not be assigned to ticket!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);

		}
	}

	/**
	 * Change specified ticket status to closed
	 * 
	 * @param ticket
	 *            Ticket to get status changed.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/closeTicket", method = RequestMethod.POST)
	public @ResponseBody String closeTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting to change ticket status to closed.");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.closeTicket(ticket)) {

			logger.info("Ticket closed successfully!");
			return new ResponseMessage().getMessageJSON(ResponseValues.SUCCESS);

		} else {

			logger.error("Ticket could not be closed!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);

		}
	}

	/**
	 * Change specified ticket status to open
	 * 
	 * @param ticket
	 *            Ticket to get status changed.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/openTicket", method = RequestMethod.POST)
	public @ResponseBody String openTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting to change ticket status to open.");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.reopenTicket(ticket)) {

			logger.info("Ticket opened successfully!");
			return new ResponseMessage().getMessageJSON(ResponseValues.SUCCESS);

		} else {

			logger.error("Ticket could not be closed!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);

		}
	}

	/**
	 * Change specified ticket priority
	 * 
	 * @param ticket
	 *            Ticket to get priority changed.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/changeTicketPriority", method = RequestMethod.POST)
	public @ResponseBody String changeTicketPriority(@RequestBody Ticket ticket) {

		logger.debug("Attempting to change ticket priority.");

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.changeTicketPriority(ticket)) {

			logger.info("Ticket priority changed successfully!");
			return new ResponseMessage().getMessageJSON(ResponseValues.SUCCESS);

		} else {

			logger.error("Failed to change the ticket priority!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);

		}
	}

	/**
	 * Change specified ticket category
	 * 
	 * @param ticket
	 *            Ticket to get category changed.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/changeTicketCategory", method = RequestMethod.POST)
	public @ResponseBody String changeTicketCategory(@RequestBody Ticket ticket) {

		logger.debug("Attempting to change ticket category.");

		TicketDAOInterface ticketDAO = new TicketDAO();

		if (ticketDAO.changeTicketCategory(ticket)) {

			logger.info("Ticket category changed successfully!");
			return new ResponseMessage().getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.info("Failed to change the ticket category!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}
	}
}