package com.cts.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
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
 * Handles requests for the application register page.
 */
@Controller
@Scope("session")
public class RegisterController implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	// Regular expression to check if the mail is in the gmail.com domain.
	private static String emailRegexp;
	private Pattern pattern;

	// Boolean variable that checks if the incoming data is passing all the
	// local tests.
	private boolean localSuccess;

	// Initializing the logger for this class.
	private static Logger logger = Logger.getLogger(RegisterController.class.getName());

	public RegisterController() {
		emailRegexp = ConfigReader.getInstance().getValueForKey("emailRegexp");
		pattern = Pattern.compile(emailRegexp);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String firstThingCalled() {

		return "index";
	}

	// Post method of the controller. the user data is hidden from the URL.
	/**
	 * @param user
	 *            The new user to be registered
	 * @return A json response if error or success
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String register(@RequestBody User user) {

		localSuccess = true;

		logger.info("######## Attempting a register...");
		try {
			// Checks if the fields are empty (eg: "")
			if (user.getEmail().trim().isEmpty()) {

				localSuccess = false;
				logger.info("Error: Email field is empty!");
				return new RegisterResponse().getMessageJson(ResponseValues.EMPTYEMAILFIELD);
			}
		} catch (NullPointerException e) {
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYEMAILFIELD);
		}
		try {
			if (user.getFirstName().trim().isEmpty()) {

				localSuccess = false;
				logger.info("Error: First Name field is empty!");
				return new RegisterResponse().getMessageJson(ResponseValues.REGISTEREMPTYFIRSTNAMEFIELD);

			}
		} catch (NullPointerException e) {
			return new RegisterResponse().getMessageJson(ResponseValues.REGISTEREMPTYFIRSTNAMEFIELD);
		}
		try {
			if (user.getLastName().trim().isEmpty()) {
				localSuccess = false;
				logger.info("Error: Last Name field is empty!");
				return new RegisterResponse().getMessageJson(ResponseValues.REGISTEREMPTYLASTNAMEFIELD);

			}
		} catch (NullPointerException e) {
			return new RegisterResponse().getMessageJson(ResponseValues.REGISTEREMPTYLASTNAMEFIELD);
		}
		try {
			if (user.getPassword().trim().isEmpty()) {
				localSuccess = false;
				logger.info("Error: Password field is empty!");
				return new RegisterResponse().getMessageJson(ResponseValues.EMPTYPASSWORDFIELD);

			}
		} catch (NullPointerException e) {
			return new RegisterResponse().getMessageJson(ResponseValues.EMPTYPASSWORDFIELD);
		}
		try {
			if (user.getTitle().trim().isEmpty()) {
				localSuccess = false;
				logger.info("Error: A title option must be selected!");
				return new RegisterResponse().getMessageJson(ResponseValues.REGISTEREMPTYTITLE);

			}
		} catch (NullPointerException e) {
			return new RegisterResponse().getMessageJson(ResponseValues.REGISTEREMPTYTITLE);
		}
		// Checks if the email is valid
		if (!isValidEmail(user.getEmail())) {

			localSuccess = false;
			logger.info("Error: Invalid Email");
			return new RegisterResponse().getMessageJson(ResponseValues.INVALIDEMAILFORMAT);
		}

		// If the data passes the local tests, the database is called
		if (localSuccess) {

			// hash the password
			user.setPassword(HashUtil.getHash((user.getPassword())));
			UserDAOInterface userDAO = new UserDAO();

			if (userDAO.createAccount(user)) {
				
				logger.info("A new account was successfully created");
				return new RegisterResponse().getMessageJson(ResponseValues.REGISTERSUCCESS);
			} else {
				
				logger.info("Error: email already exists.");
				return new RegisterResponse().getMessageJson(ResponseValues.REGISTEREXISTINGEMAIL);
			}
		}
		logger.info("Unknown error.");
		return new RegisterResponse().getMessageJson(ResponseValues.UNKNOWN);
	}

	/**
	 * @param email
	 *            The email to be verified if it is in cerner.com domain
	 * @return true if the email is in the cerner.com domain, false if it is in
	 *         any other domain.
	 */
	private boolean isValidEmail(String email) {
		
		Matcher matcher = pattern.matcher(email);

		logger.info(matcher.matches() + " for " + email);

		if (!matcher.matches()) {

			return false;
		}
		return true;
	}
}
