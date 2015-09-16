package com.cts.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.CategoryResponse;
import com.cts.communication.ResponseValues;
import com.cts.communication.TicketResponse;
import com.cts.dao.CategoryDAO;
import com.cts.dao.CategoryDAOInterface;
import com.cts.dao.TicketDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.Ticket;
import com.cts.entities.ViewTicketsRequest;

public class RootUserManagementController {
	
	private static Logger logger = Logger.getLogger(RootUserManagementController.class.getName());

	@RequestMapping(value = "/viewUsers", method = RequestMethod.POST)
	public @ResponseBody String viewUsers(@RequestBody ViewUsersRequest viewUserRequest) {

		logger.info("Attempting to send the list of user ...");

		UserDAOInterface userDAO = new UserDAO();
		StringBuilder totalNumberOfPages = new StringBuilder();
		ArrayList<Ticket> users = userDAO.getTickets(viewUserRequest, totalNumberOfPages);
		String usersJson;
		ObjectMapper objectMapper = new ObjectMapper();
		try {

			usersJson = objectMapper.writeValueAsString(users);
		} catch (IOException e) {

			logger.error("Json error when trying to map the array of users.");
			return new TicketResponse().getMessageJson(ResponseValues.UNKNOWN); // brain damage
		}
		logger.info("The list of users was successfully retrieved!");
		return "{\"totalNumberOfPages\":" + totalNumberOfPages + ",\"users\":" + usersJson + "}";
	}
	
	// get categories admin status for this user ...
	
}
