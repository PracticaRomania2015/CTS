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
import com.cts.entities.UserForUpdate;
import com.cts.utils.HashUtil;

/**
 * Handles requests for the user properties page.
 */
@Controller
public class UserPersonalDataController {

	private static Logger logger = Logger.getLogger(UserPersonalDataController.class.getName());

	/**
	 * Load user details
	 * 
	 * @param user
	 *            User to which to load details
	 * @return JSON with user or error response.
	 */
	@RequestMapping(value = "/loadUserPersonalData", method = RequestMethod.POST)
	public @ResponseBody String loadUserPersonalData(@RequestBody User user) {

		logger.debug("Attempting to get user personal data.");
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.getUserData(user)) {

			logger.debug("User data retrieved successfully");
			return new ResponseMessage(user).getMessageJSON(ResponseValues.SUCCESS);
		} else {

			logger.debug("Failed to retrive user data.");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}
	}

	/**
	 * Update user details
	 * 
	 * @param user
	 *            User to which to update details
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/updateUserPersonalData", method = RequestMethod.POST)
	public @ResponseBody String updateUserPersonalData(@RequestBody UserForUpdate user) {

		logger.debug("Attempting to update a user's personal data.");

		// User title validation
		if (user.getTitle() == null) {

			logger.error("Title is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYTITLE);
		}

		if (user.getTitle().equals("")) {

			logger.error("Title is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYTITLE);
		}

		// User first name validation
		if (user.getFirstName() == null) {

			logger.error("First Name is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYFIRSTNAME);
		}

		if (user.getFirstName().equals("")) {

			logger.error("First Name is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYFIRSTNAME);
		}

		// User last name validation
		if (user.getLastName() == null) {

			logger.error("Last Name is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYLASTNAME);
		}

		if (user.getLastName().equals("")) {

			logger.error("Last Name is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYLASTNAME);
		}

		// User email validation
		if (user.getEmail() == null) {

			logger.error("Email is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}

		if (user.getEmail().equals("")) {

			logger.error("Email is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}

		// User old password validation
		if (user.getOldPassword() == null) {

			logger.error("Old password is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.UPDATEUSEREMPTYOLDPASSWORD);
		}

		if (user.getOldPassword().equals("")) {

			logger.error("Old password is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.UPDATEUSEREMPTYOLDPASSWORD);
		}

		// User new password validation
		if (user.getPassword() == null) {

			logger.error("New password is null or empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYPASSWORD);
		}

		// Update user properties
		user.setOldPassword(HashUtil.getHash((user.getOldPassword())));
		user.setPassword(HashUtil.getHash((user.getPassword())));

		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.updateUserPersonalData(user)) {

			logger.info("Account updated successfully!");
			return new ResponseMessage().getMessageJSON(ResponseValues.UPDATEUSERSUCCESS);
		} else {

			logger.error("Account could not be updated!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}
	}
}