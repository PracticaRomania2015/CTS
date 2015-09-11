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
 * Handle request for view tickets.
 */
@Controller
public class ViewTicketsController {

	private static Logger logger = Logger.getLogger(LoginController.class.getName());

	/**
	 * POST method for view tickets controller.
	 * 
	 * @param user
	 * @return list of tickets or error message.
	 */
	@RequestMapping(value = "/viewTickets", method = RequestMethod.POST)
	public @ResponseBody String viewTickets(@RequestBody ViewTicketsRequest viewTicketsRequest) {

		logger.info("Attempting to send the list of tickets ...");

		TicketDAOInterface ticketDAO = new TicketDAO();
		StringBuilder totalNumberOfPages = new StringBuilder();
		ArrayList<Ticket> tickets = ticketDAO.getTickets(viewTicketsRequest, totalNumberOfPages);
		String ticketsJson;
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			ticketsJson = objectMapper.writeValueAsString(tickets);
		} catch (IOException e) {

			logger.info("Json error when trying to map the array of tickets.");
			return new TicketResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
		logger.info("The list of tickets was successfully retrieved!");
		return "{\"totalNumberOfPages\":" + totalNumberOfPages + ",\"tickets\":" + ticketsJson + "}";
	}
}
