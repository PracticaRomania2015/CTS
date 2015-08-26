package com.cts.controllers;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Scope("session")
public class RecoveryPasswordController {

	private static Logger logger = Logger.getLogger(LoginController.class.getName());

	/**
	 * @param email
	 * @return message that will be displayed on ui.
	 */
	@RequestMapping(value = "/recoveryPassword", method = RequestMethod.POST)
	@ResponseBody
	public String recoveryPassword(@RequestBody User user) {

		String subject = "New password";
		
		logger.info("Attempting to recover the password for the following email: " + user.getEmail());

		// Generate a new password
		// if the specified email is valid then modify the password with the
		// generated one and send it via email.
		GenerateRandomPassword randomPassword = new GenerateRandomPassword(10);
		String newPassword = randomPassword.nextString();
		String msg = "Your new password is " + newPassword;
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.checkEmailForRecoveryPassword(user.getEmail(), HashUtil.getHash(newPassword))
				&& SendEmail.sendEmail(user.getEmail(), subject, msg)) {

			logger.info("The password was changed and was send via email!");
			ObjectMapper objectMapper = new ObjectMapper();
			try {

				return objectMapper.writeValueAsString("A new password was send to specified email address!");
			} catch (IOException e) {

				return "Error while trying to recover the password!";
			}
		} else {

			logger.info("The specified email is incorrect!");
			return "The specified email is incorrect!";
		}
	}
}
