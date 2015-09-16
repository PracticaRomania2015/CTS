package com.cts.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.RegisterResponse;
import com.cts.communication.ResponseValues;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.User;
import com.cts.utils.ConfigReader;
import com.cts.utils.HashUtil;

/**
 * Handles requests for the register page.
 */
@Controller
public class RegisterController{
	
	// Regular expression for email validation.
	private static String emailRegexp;
	private Pattern pattern;

	// Initializing the logger for this class.
	private static Logger logger = Logger.getLogger(RegisterController.class.getName());

	public RegisterController() {
		
		emailRegexp = ConfigReader.getInstance().getValueForKey("emailRegexp");
		pattern = Pattern.compile(emailRegexp);
	}
	
	/**
	 * @param email The email to be verified if matches the pattern provided
	 * @return true if the email is in the cerner.com domain, false if it is in
	 *         any other domain.
	 */
	private boolean isValidEmail(String email) {
		
		Matcher matcher = pattern.matcher(email);
		logger.info("DEBUG: " + matcher.matches() + " for " + email + ".");
		if (!matcher.matches()) {

			return false;
		}
		return true;
	}

	/**
	 * Register user
	 * 
	 * @param user The new user to be registered
	 * @return A json response if error or success
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String register(@RequestBody User user) {

		logger.info("DEBUG: Attempting to register a user.");

		// User first name validation
		if (user.getFirstName() == null){
			
			logger.info("ERROR: First name is null!");
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYFIRSTNAME);
		}
		if (user.getFirstName().equals("")) {
			
			logger.info("ERROR: First name is empty!");
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYFIRSTNAME);
		}
		
		// User last name validation
		if (user.getLastName() == null){
			
			logger.info("ERROR: Last name is null!");
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYLASTNAME);
		}
		if (user.getLastName().equals("")) {
			
			logger.info("ERROR: Last name is empty!");
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYLASTNAME);
		}
		
		
		// User email validation
		if (user.getEmail() == null){
			
			logger.info("ERROR: Last name is null!");
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYEMAIL);
		}
		if (user.getEmail().equals("")) {
			
			logger.info("ERROR: Last name is empty!");
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYEMAIL);
		}
		if (!isValidEmail(user.getEmail())) {

			logger.info("ERROR: Invalid Email");
			return new RegisterResponse().getMessageJson(ResponseValues.INVALIDEMAILFORMAT);
		}
		
		// User password validation
		if (user.getPassword() == null){
			
			logger.info("ERROR: Password is null!");
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYPASSWORD);
		}
		if (user.getPassword().equals("")) {
			
			logger.info("ERROR: Password is empty!");
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYPASSWORD);
		}
		
		// Account creation
		user.setPassword(HashUtil.getHash((user.getPassword())));
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.createAccount(user)) {
			
			logger.info("INFO: A new account was created successfully!");
			return new RegisterResponse().getMessageJson(ResponseValues.REGISTERSUCCESS);
		} else {
			
			logger.info("WARN: The email already exists!");
			return new RegisterResponse().getMessageJson(ResponseValues.REGISTEREXISTINGEMAIL);
		}
	}
}
