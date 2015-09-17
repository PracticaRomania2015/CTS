package com.cts.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.RecoveryResponse;
import com.cts.communication.ResponseValues;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.User;
import com.cts.utils.GenerateRandomPassword;
import com.cts.utils.HashUtil;
import com.cts.utils.SendEmail;

/**
 * Handles requests for recovery password page.
 */
@Controller
public class RecoveryController {

	private static Logger logger = Logger.getLogger(RecoveryController.class.getName());

	/**
	 * Reset password and send a new one by email
	 * 
	 * @param user User with email to reset password.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/recoveryPassword", method = RequestMethod.POST)
	public @ResponseBody String recoveryPassword(@RequestBody User user) {

		// Parameter validation
		if (user == null){
			
			logger.error("User is null!");
			return new RecoveryResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		logger.debug("Attempting to recover the password for the following email: " + user.getEmail() + ".");
		
		// Email validation
		if (user.getEmail() == null){
			
			logger.error("Email is null!");
			return new RecoveryResponse().getMessageJson(ResponseValues.EMPTYEMAIL);
		}
		if (user.getEmail().equals("")) {
			
			logger.error("Email is empty!");
			return new RecoveryResponse().getMessageJson(ResponseValues.EMPTYEMAIL);
		}
		
		// Recovery mail contents
		String subject = "New password";
		GenerateRandomPassword randomPassword = new GenerateRandomPassword(10);
		String newPassword = randomPassword.nextString();
		String msg = "Your new password is " + newPassword;
		
		// Send the password to the specified email
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.resetPassword(user.getEmail(), HashUtil.getHash(newPassword))
				&& SendEmail.sendEmail(user.getEmail(), subject, msg)) {

			logger.info("The password was changed and was send via email!");
			return new RecoveryResponse().getMessageJson(ResponseValues.RECOVERYSUCCESS);
		} else {

			logger.warn("The specified email is incorrect!");
			return new RecoveryResponse().getMessageJson(ResponseValues.RECOVERYINCORRECTEMAIL);
		}
	}
}
