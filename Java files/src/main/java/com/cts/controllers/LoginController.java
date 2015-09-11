package com.cts.controllers;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.LoginResponse;
import com.cts.communication.ResponseValues;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.User;
import com.cts.utils.HashUtil;

/**
 * Handle requests for login page.
 */
@Controller
public class LoginController {

	private static Logger logger = Logger.getLogger(LoginController.class.getName());

	/**
	 * @param user
	 * @return user object in json format.
	 */
	protected String getUserObjectInJsonFormat(User user) {

		String jsonMessage = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonMessage = objectMapper.writeValueAsString(user);
		} catch (IOException e) {
		}
		return jsonMessage;
	}

	/**
	 * Post method for the login controller.
	 * 
	 * @param userLoginJson
	 * @return full user details if the login was successfully or error message
	 *         otherwise
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestBody User user) {

		logger.info("Attempting a login ...");

		// Check if the username is null or empty.
		if (user.getEmail() == null || user.getEmail().equals("")) {
			
			logger.info("Username is null or empty.");
			return new LoginResponse().getMessageJson(ResponseValues.EMPTYEMAILFIELD);
		}

		// Check if the password is null or empty.
		if (user.getPassword() == null || user.getPassword().equals("")) {
			
			logger.info("Password is null or empty.");
			return new LoginResponse().getMessageJson(ResponseValues.EMPTYPASSWORDFIELD);
		}

		// Validate the login.
		user.setPassword(HashUtil.getHash((user.getPassword())));
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.validateLogin(user)) {

			logger.info("Login succesfully!");
			String userJson = getUserObjectInJsonFormat(user);
			if (userJson != null) {	
				return userJson;
			} else {
				return new LoginResponse().getMessageJson(ResponseValues.UNKNOWN);
			}
		} else {
			
			logger.info("Login information is not correct.");
			return new LoginResponse().getMessageJson(ResponseValues.LOGININVALIDCREDENTIALS);
		}
	}
}