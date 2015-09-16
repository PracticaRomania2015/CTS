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
 * Handles requests for the login page.
 */
@Controller
public class LoginController {

	private static Logger logger = Logger.getLogger(LoginController.class.getName());

	/**
	 * Convert user instance to JSON
	 * 
	 * @param user User instance to be converted in JSON.
	 * @return User instance in JSON format.
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
	 * Authenticate user
	 * 
	 * @param user User to be logged in.
	 * @return JSON with complete user details.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestBody User user) {

		logger.debug("Attempting a login a user.");

		// Email validation
		if (user.getEmail() == null){
					
			logger.error("Email is null!");
			return new LoginResponse().getMessageJson(ResponseValues.EMPTYEMAIL);
		}
		if (user.getEmail().equals("")) {
				
			logger.error("Email is empty!");
			return new LoginResponse().getMessageJson(ResponseValues.EMPTYEMAIL);
		}
		
		// Password validation
		if (user.getPassword() == null){
					
			logger.error("Password is null!");
			return new LoginResponse().getMessageJson(ResponseValues.EMPTYPASSWORD);
		}
		if (user.getPassword().equals("")) {
				
			logger.error("Password is empty!");
			return new LoginResponse().getMessageJson(ResponseValues.EMPTYPASSWORD);
		}
		
		// Login credentials validation
		user.setPassword(HashUtil.getHash((user.getPassword())));
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.validateLogin(user)) {

			logger.info("User logged in succesfully!");
			String userJson = getUserObjectInJsonFormat(user);
			if (userJson != null) {	
				
				return userJson;
			} else {
				
				return new LoginResponse().getMessageJson(ResponseValues.UNKNOWN);
			}
		} else {
			
			logger.warn("Login information is not correct!");
			return new LoginResponse().getMessageJson(ResponseValues.LOGININVALIDCREDENTIALS);
		}
	}
}