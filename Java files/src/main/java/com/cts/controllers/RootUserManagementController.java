package com.cts.controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.ResponseValues;
import com.cts.communication.UserRightsResponse;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.User;
import com.cts.entities.UserStatus;
import com.cts.entities.ViewUsersRequest;

public class RootUserManagementController {
	
	private static Logger logger = Logger.getLogger(RootUserManagementController.class.getName());

	@RequestMapping(value = "/viewUsers", method = RequestMethod.POST)
	public @ResponseBody String viewUsers(@RequestBody ViewUsersRequest viewUserRequest) {

		logger.info("Attempting to send the list of user ...");

		UserDAOInterface userDAO = new UserDAO();
		StringBuilder totalNumberOfPages = new StringBuilder();
		ArrayList<User> users = userDAO.getUsers(viewUserRequest, totalNumberOfPages);
		String usersJson;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			usersJson = objectMapper.writeValueAsString(users);
			logger.info("The list of users was successfully retrieved!");
			return "{\"totalNumberOfPages\":" + totalNumberOfPages + ",\"users\":" + usersJson + "}";
		} catch (IOException e) {

			logger.error("Json error when trying to map the array of users.");
			return new UserRightsResponse().getMessageJson(ResponseValues.UNKNOWN);
		}
	}
	
	// get categories admin status for this user ...
	@RequestMapping(value = "/getUserAdminStatus", method = RequestMethod.POST)

		
		//TODO: implement me
		
		return null;
		
	}
	
	// Alter admin rights of user ...
	@RequestMapping(value = "/setUserAdminStatus", method = RequestMethod.POST)

			
		//TODO: implement me
			
		return null;
			
	}
	
}
