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
	
	private static String emailRegexp;
	private Pattern pattern;

	private static Logger logger = Logger.getLogger(RegisterController.class.getName());

	public RegisterController() {
		
		emailRegexp = ConfigReader.getInstance().getValueForKey("emailRegexp");
		pattern = Pattern.compile(emailRegexp);
	}
	
	/**
	 * Check if email matches the pattern
	 * 
	 * @param email Email to be verified if matches the pattern provided.
	 * @return True if the email matches the regex pattern, false otherwise.
	 */
	private boolean isValidEmail(String email) {
		
		Matcher matcher = pattern.matcher(email);
		logger.debug(matcher.matches() + " for " + email + ".");
		if (!matcher.matches()) {

			return false;
		}
		return true;
	}

	/**
	 * Register user
	 * 
	 * @param user The new user to be registered.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String register(@RequestBody User user) {

		logger.debug("Attempting to register a user.");

		// Title validation
		if (user.getTitle() == null){
			
			logger.error("Title is null!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYTITLE);
		}
		if (user.getTitle().equals("")) {
			
			logger.error("Title is empty!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYTITLE);
		}
		
		// User first name validation
		if (user.getFirstName() == null){
			
			logger.error("First name is null!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYFIRSTNAME);
		}
		if (user.getFirstName().equals("")) {
			
			logger.error("First name is empty!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYFIRSTNAME);
		}
		
		// User last name validation
		if (user.getLastName() == null){
			
			logger.error("Last name is null!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYLASTNAME);
		}
		if (user.getLastName().equals("")) {
			
			logger.error("Last name is empty!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYLASTNAME);
		}
		
		// User email validation
		if (user.getEmail() == null){
			
			logger.error("Last name is null!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}
		if (user.getEmail().equals("")) {
			
			logger.error("Last name is empty!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}
		if (!isValidEmail(user.getEmail())) {

			logger.error("Invalid Email");
			return new RegisterResponse().getMessageJSON(ResponseValues.INVALIDEMAILFORMAT);
		}
		
		// User password validation
		if (user.getPassword() == null){
			
			logger.error("Password is null!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYPASSWORD);
		}
		if (user.getPassword().equals("")) {
			
			logger.error("Password is empty!");
			return new RegisterResponse().getMessageJSON(ResponseValues.EMPTYPASSWORD);
		}
		
		// Account creation
		user.setPassword(HashUtil.getHash((user.getPassword())));
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.createAccount(user)) {
			
			logger.info("A new account was created successfully!");
			return new RegisterResponse().getMessageJSON(ResponseValues.REGISTERSUCCESS);
		} else {
			
			logger.warn("An account with the provided email already exists!");
			return new RegisterResponse().getMessageJSON(ResponseValues.REGISTEREXISTINGEMAIL);
		}
	}
}
