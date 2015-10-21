package com.cts.controllers;

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
import com.cts.utils.HashUtil;

/**
 * Handles requests for the login page.
 */
@Controller
public class LoginController {

	private static Logger logger = Logger.getLogger(LoginController.class.getName());

	/**
	 * Authenticate user
	 * 
	 * @param user
	 *            User to be logged in.
	 * @return JSON with complete user details.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestBody User user) {

		logger.debug("Attempting to login a user.");

		// Email validation
		if (user.getEmail() == null || user.getEmail().equals("")) {
			logger.error("Email is null!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}

		// Password validation
		if (user.getPassword() == null || user.getPassword().equals("")) {
			logger.error("Password is null!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYPASSWORD);
		}

		// Login credentials validation
		user.setPassword(HashUtil.getHash((user.getPassword())));
		UserDAOInterface userDAO = new UserDAO();

		if (userDAO.validateLogin(user)) {
			logger.info("User logged in successfully!");
			return new ResponseMessage(user).getMessageJSON(ResponseValues.SUCCESS);
		} else {
			logger.warn("Account credentials are invalid!");
			return new ResponseMessage().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS);
		}
	}
}