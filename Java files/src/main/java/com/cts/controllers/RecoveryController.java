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
 * Handle requests for recovery password page.
 */
@Controller
public class RecoveryController {

	private static Logger logger = Logger.getLogger(RecoveryController.class.getName());

	/**
	 * Reset password and send new one by email
	 * 
	 * @param email
	 * @return message that will be displayed on ui.
	 */
	@RequestMapping(value = "/recoveryPassword", method = RequestMethod.POST)
	public @ResponseBody String recoveryPassword(@RequestBody User user) {

		logger.info("DEBUG: Attempting to recover the password for the following email: " + user.getEmail() + ".");
		
		// Email validation
		if (user.getEmail() == null){
			
			logger.info("ERROR: Email is null!");
			return new RecoveryResponse().getMessageJson(ResponseValues.EMPTYEMAIL);
		}
		if (user.getEmail().equals("")) {
			
			logger.info("ERROR: Email is empty!");
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

			logger.info("INFO: The password was changed and was send via email!");
			return new RecoveryResponse().getMessageJson(ResponseValues.RECOVERYSUCCESS);
		} else {

			logger.info("WARN: The specified email is incorrect!");
			return new RecoveryResponse().getMessageJson(ResponseValues.RECOVERYINCORRECTEMAIL);
		}
	}
}
