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
	//TODO:
	//CHANGED FROM: /recoveryPassword
	@RequestMapping(value = "/recovery", method = RequestMethod.POST)
	public @ResponseBody String recoveryPassword(@RequestBody User user) {
		
		logger.debug("Attempting to reset the password for an account.");
		
		// Email validation
		if (user.getEmail() == null){
			
			logger.error("Email is null!");
			return new RecoveryResponse().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}
		if (user.getEmail().equals("")) {
			
			logger.error("Email is empty!");
			return new RecoveryResponse().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}
		
		// Recovery mail contents
		String subject = "CTS - Password Reset";
		GenerateRandomPassword randomPassword = new GenerateRandomPassword(10);
		String newPassword = randomPassword.nextString();
		String msg = "Your new password is " + newPassword;
		
		// Send the password to the specified email
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.resetPassword(user.getEmail(), HashUtil.getHash(newPassword)) && SendEmail.sendEmail(user.getEmail(), subject, msg)) {

			logger.info("The password was changed and was sent via email!");
			return new RecoveryResponse().getMessageJSON(ResponseValues.RECOVERYSUCCESS);
		} else {

			logger.warn("No account with the provided email was found!");
			return new RecoveryResponse().getMessageJSON(ResponseValues.RECOVERYINCORRECTEMAIL);
		}
	}
}
