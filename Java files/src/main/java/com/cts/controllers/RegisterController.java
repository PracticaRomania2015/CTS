package com.cts.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cts.communication.ResponseMessage;
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
public class RegisterController {

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
	 * @param email
	 *            Email to be verified if matches the pattern provided.
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
	 * @param user
	 *            The new user to be registered.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String register(@RequestBody User user) {

		logger.debug("Attempting to register a user.");

		// Title validation
		if (user.getTitle() == null || user.getTitle().equals("")) {
			logger.error("Title is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYTITLE);
		}

		// User first name validation
		if (user.getFirstName() == null || user.getFirstName().equals("")) {
			logger.error("First name is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYFIRSTNAME);
		}

		// User last name validation
		if (user.getLastName() == null || user.getLastName().equals("")) {
			logger.error("Last name is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYLASTNAME);
		}

		// User email validation
		if (user.getEmail() == null || user.getEmail().equals("")) {
			logger.error("Last name is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}

		// Check email format
		if (!isValidEmail(user.getEmail())) {
			logger.error("Invalid Email");
			return new ResponseMessage().getMessageJSON(ResponseValues.INVALIDEMAILFORMAT);
		}

		// User password validation
		if (user.getPassword() == null || user.getPassword().equals("")) {
			logger.error("Password is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYPASSWORD);
		}

		// User questions validation
		if (user.getQuestion_1().getQuestion() == null || user.getQuestion_1().getQuestion().equals("")
				|| user.getQuestion_2().getQuestion() == null || user.getQuestion_2().getQuestion().equals("")) {
			logger.error("Questions are not set properly!");
			return new ResponseMessage().getMessageJSON(ResponseValues.QUESTIONSERROR);
		}

		// User questions answers validation
		if (user.getQuestionAnswer_1() == null || user.getQuestionAnswer_1().equals("")
				|| user.getQuestionAnswer_2() == null || user.getQuestionAnswer_2().equals("")) {
			logger.error("Questions answers are not set properly!");
			return new ResponseMessage().getMessageJSON(ResponseValues.QUESTIONSANSWERSERROR);
		}

		// Account creation
		user.setPassword(HashUtil.getHash(user.getPassword()));
		user.setQuestionAnswer_1(HashUtil.getHash(user.getQuestionAnswer_1().toLowerCase()));
		user.setQuestionAnswer_2(HashUtil.getHash(user.getQuestionAnswer_2().toLowerCase()));
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.createAccount(user)) {
			logger.info("A new account was created successfully!");
			return new ResponseMessage().getMessageJSON(ResponseValues.REGISTERSUCCESS);
		} else {
			logger.warn("An account with the provided email already exists!");
			return new ResponseMessage().getMessageJSON(ResponseValues.REGISTEREXISTINGEMAIL);
		}
	}
}