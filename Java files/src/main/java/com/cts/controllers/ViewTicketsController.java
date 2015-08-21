package com.cts.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cts.dao.TicketDAOInterface;
import com.cts.dao.TicketDAO;
import com.cts.entities.Ticket;
import com.cts.entities.User;

/**
 * Handle request for view tickets.
 */
@Controller
public class ViewTicketsController {

	private static final Logger logger = Logger.getLogger(LoginController.class.getName());
	private FileHandler fileHandler;

	/**
	 * POST method for view tickets controller.
	 * 
	 * @param user
	 * @return list of tickets or error message.
	 */
	@RequestMapping(value = "/viewTickets", method = RequestMethod.POST)
	@ResponseBody
	public String viewTickets(@RequestBody User user) {

		try {
			// This block configure the logger with handler and formatter.
			fileHandler = new FileHandler("logs\\ApplicationLogFile.log", true);
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(false);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
		} catch (SecurityException e) {
		} catch (IOException e) {
		}

		logger.info("Attempting to send the list of tickets ...");

		TicketDAOInterface ticketDAO = new TicketDAO();
		ArrayList<Ticket> tickets = ticketDAO.getTickets(user);

		String jsonMessage = "json error";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonMessage = objectMapper.writeValueAsString(tickets);
		} catch (IOException e) {
		}
		return jsonMessage;
	}
}
