package com.cts.controllers;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.TicketError;
import com.cts.dao.TicketDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.entities.Ticket;

/**
 * Handle requests for view and response to tickets page.
 */
@Controller
@Scope("session")
public class TicketViewAndResponseController {

	/**
	 * POST method for the controller.
	 * 
	 * @param ticket
	 * @return ticket object with comments or error message.
	 */
	@RequestMapping(value = "/viewTicket", method = RequestMethod.POST)
	@ResponseBody
	public String viewTicketComments(@RequestBody Ticket ticket) {

		TicketDAOInterface ticketDAO = new TicketDAO();
		ticket.setComments(ticketDAO.getTicketComments(ticket));
		String jsonMessage = "json error";
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			jsonMessage = objectMapper.writeValueAsString(ticket);
		} catch (IOException e) {
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

		if (ticket == null || ticket.getNewTicketComment() == null || ticket.getNewTicketComment().getComment() == null
				|| ticket.getNewTicketComment().getComment().equals("")) {

			return new TicketError().getErrorJson(5);
		}

		TicketDAOInterface ticketDAO = new TicketDAO();
		if (ticketDAO.addCommentToTicket(ticket)) {

			ObjectMapper objectMapper = new ObjectMapper();
			try {

				return objectMapper.writeValueAsString(ticket);
			} catch (IOException e) {

				return new TicketError().getErrorJson(-1);
			}
		} else {

			return new TicketError().getErrorJson(4);
		}
	}
}