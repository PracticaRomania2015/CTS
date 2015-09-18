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
import com.cts.entities.Ticket;
import com.cts.entities.ViewTicketsRequest;


/**
 * Handles requests for the tickets.
 */
@Controller
public class TicketsController {

	private static Logger logger = Logger.getLogger(TicketsController.class.getName());
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Get details of provided ticket
	 * 
	 * @param ticket Ticket to get details for.
	 * @return Complete ticket in JSON format.
	 */
	@RequestMapping(value = "/viewTicket", method = RequestMethod.POST)
	public @ResponseBody String viewTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting to retrieve full details and comments for a ticket.");
		
		// Parameter validation
		if (ticket == null){
			
			logger.error("Ticket is null!");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.getFullTicket(ticket)) {
			logger.info("Full ticket received successfully from db.");
		}

		String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		try {

			jsonMessage = objectMapper.writeValueAsString(ticket);
		} catch (IOException e) {

			logger.error("Json error when trying to map the ticket object.");
		}
		return jsonMessage;
	}
	
	/**
	 * Get particular list of tickets
	 * 
	 * @param viewTicketsRequest ViewTicketsRequest with details regarding which tickets to receive.  
	 * @return List of tickets in JSON format.
	 */
	@RequestMapping(value = "/viewTickets", method = RequestMethod.POST)
	public @ResponseBody String viewTickets(@RequestBody ViewTicketsRequest viewTicketsRequest) {

		logger.debug("Attempting to send the list of tickets ...");

		// Parameter validation
		if (viewTicketsRequest == null){
			
			logger.error("viewTicketsRequest is null!");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// Getting tickets
		TicketDAOInterface ticketDAO = new TicketDAO();
		StringBuilder totalNumberOfPages = new StringBuilder();
		ArrayList<Ticket> tickets = ticketDAO.getTickets(viewTicketsRequest, totalNumberOfPages);
		String ticketsJson;
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			ticketsJson = objectMapper.writeValueAsString(tickets);
		} catch (IOException e) {

			logger.error("Json error when trying to map the array of tickets.");
			return new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
		logger.info("The list of tickets was successfully retrieved!");
		return "{\"totalNumberOfPages\":" + totalNumberOfPages + ",\"tickets\":" + ticketsJson + "}";
	}
	
	/**
	 * Submit ticket
	 * 
	 * @param ticket Ticket to be submitted.
	 * @return Ticket with complete details in JSON format.
	 */
	@RequestMapping(value = "/submitTicket", method = RequestMethod.POST)
	public @ResponseBody String submitTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting a ticket submission.");

		// Parameter validation
		if (ticket == null){
			
			logger.error("Ticket is null!");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// Ticket subject validation
		if (ticket.getSubject() == null){
			
			logger.error("Ticket subject is null!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYSUBJECT);
		}
		if (ticket.getSubject().equals("")) {
			
			logger.error("Ticket subject is empty!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYSUBJECT);
		}
		
		// Ticket category validation
		if (ticket.getCategory() == null){
			
			logger.error("Ticket category is null!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCATEGORY);
		}
		if (ticket.getCategory().equals("")) {
			
			logger.error("Ticket category is empty!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCATEGORY);
		}

		// Ticket description validation
		if (ticket.getComments().get(0).getComment() == null){
			
			logger.error("Ticket description is null!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYDESCRIPTION);
		}
		if (ticket.getComments().get(0).getComment().equals("")) {
			
			logger.error("Ticket description is empty!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYDESCRIPTION);
		}

		// Ticket creation.
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.createTicket(ticket)) {

			logger.info("INFO: Ticket submitted succesfully!");
			String jsonMessage = new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
			try {
				jsonMessage = objectMapper.writeValueAsString(ticket);
				System.out.println(jsonMessage);
			} catch (IOException e) {
			}
			return jsonMessage;
		} else {

			logger.info("ERROR: Database error when trying to create a new ticket.");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}
	
	/**
	 * Add comment to specified ticket
	 * 
	 * @param ticket Ticket to which to add comment to.
	 * @return Ticket with added comment details from database in JSON format.
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public @ResponseBody String addComment(@RequestBody Ticket ticket) {

		logger.debug("Attempting to add a comment for a ticket.");

		// Parameter validation
		if (ticket == null){
			
			logger.error("Ticket is null!");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// New comment validation
		if (ticket.getComments().get(0).getComment() == null){
			
			logger.error("Ticket comment is null!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCOMMENT);
		}
		if (ticket.getComments().get(0).getComment().equals("")) {
			
			logger.error("Ticket comment is empty!");
			return new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCOMMENT);
		}

		// Adding comment to ticket
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.addCommentToTicket(ticket)) {

			try {

				logger.info("The comment was added successfully!");
				return objectMapper.writeValueAsString(ticket);
			} catch (IOException e) {

				logger.error("Error while trying to map the json for ticket object.");
				return new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
			}
		} else {

			logger.error("Database error while trying to add a new comment!");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);
		}
	}
	
	/**
	 * Assign user to a specified ticket
	 * 
	 * @param ticket Ticket to which to assign the user
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/assignUserToTicket", method = RequestMethod.POST)
	public @ResponseBody String assignTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting to assign an user to a ticket.");
		
		// Parameter validation
		if (ticket == null){
			
			logger.error("Ticket is null!");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}
		
	
		
		// TicketId Validation
		if (ticket.getTicketId() == 0){
			
			logger.error("Invalid TicketId");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}

		// Assigning user to ticket
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.assignTicket(ticket)) {

			logger.info("Ticket assigned to user successfully!");
			return new TicketResponse().getMessageJson(ResponseValues.SUCCESS);

		} else {

			logger.error("Database error while trying to assign user to ticket!");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);

		}
	}
	
	/**
	 * Change specified ticket status to closed
	 * 
	 * @param ticket Ticket to get status changed.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/closeTicket", method = RequestMethod.POST)
	public @ResponseBody String closeTicket(@RequestBody Ticket ticket) {

		logger.debug("Attempting to change ticket status to closed.");
		
		// Parameter validation
		if (ticket == null){
			
			logger.error("Ticket is null!");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// TicketId Validation
		if (ticket.getTicketId() == 0){
			
			logger.error("Invalid TicketId");
			return new TicketResponse().getMessageJson(ResponseValues.ERROR);
		}

		// Changing ticket status to closed
		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.closeTicket(ticket)) {

			logger.info("Ticket closed successfully!");
			return new TicketResponse().getMessageJson(ResponseValues.SUCCESS);

		} else {

			logger.error("Database error while trying to close ticket!");
			return new TicketResponse().getMessageJson(ResponseValues.DBERROR);

		}
	}
}