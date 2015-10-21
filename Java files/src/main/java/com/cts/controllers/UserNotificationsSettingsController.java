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
import com.cts.entities.UserNotificationsSettings;

/**
 * Handles requests for the user notifications settings page.
 */
@Controller
public class UserNotificationsSettingsController {

	private static Logger logger = Logger.getLogger(UserNotificationsSettingsController.class.getName());

	/**
	 * Load user notifications settings.
	 * 
	 * @param userNotificationsSettings
	 *            containing the user with the user id.
	 * @return full user notifications settings.
	 */
	@RequestMapping(value = "/loadUserNotificationsSettings", method = RequestMethod.POST)
	public @ResponseBody String loadUserNotificationsSettings(
			@RequestBody UserNotificationsSettings userNotificationsSettings) {

		logger.info("Attempting to get user notifications settings.");

		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.getUserNotificationsSettings(userNotificationsSettings)) {
			logger.info("User notifications settings retrieved successfully");
			return new ResponseMessage(userNotificationsSettings).getMessageJSON(ResponseValues.SUCCESS);
		} else {
			logger.error("Failed to retrive user notifications settings.");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}
	}

	/**
	 * Update user notifications settings.
	 * 
	 * @param userNotificationsSettings.
	 * @return success or error.
	 */
	@RequestMapping(value = "/updateUserNotificationsSettings", method = RequestMethod.POST)
	public @ResponseBody String updateUserNotificationsSettings(
			@RequestBody UserNotificationsSettings userNotificationsSettings) {

		logger.info("Attempting to update a user's notifications settings.");

		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.updateUserNotificationsSettings(userNotificationsSettings)) {
			logger.info("User notifications settings updated successfully!");
			return new ResponseMessage().getMessageJSON(ResponseValues.UPDATEUSERNOTIFICATIONSSETTINGSSUCCESS);
		} else {
			logger.error("User notifications settings could not be updated!");
			return new ResponseMessage().getMessageJSON(ResponseValues.DBERROR);
		}
	}
}