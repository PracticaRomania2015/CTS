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
	 * @param user
	 *            User with email to reset password.
	 * @return JSON with success/error response.
	 */
	@RequestMapping(value = "/recovery", method = RequestMethod.POST)
	public @ResponseBody String recoveryPassword(@RequestBody User user) {

		logger.debug("Attempting to reset the password for an account.");

		// Email validation
		if (user.getEmail() == null) {

			logger.error("Email is null!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}
		if (user.getEmail().equals("")) {

			logger.error("Email is empty!");
			return new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL);
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

		// Recovery mail contents
		String subject = "CTS - Password Reset";
		GenerateRandomPassword randomPassword = new GenerateRandomPassword(10);
		String newPassword = randomPassword.nextString();
		String msg = "Your new password is " + newPassword;
		user.setPassword(HashUtil.getHash(newPassword));
		user.setQuestionAnswer_1(HashUtil.getHash(user.getQuestionAnswer_1().toLowerCase()));
		user.setQuestionAnswer_2(HashUtil.getHash(user.getQuestionAnswer_2().toLowerCase()));

		// Send the password to the specified email
		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.resetPassword(user) && SendEmail.sendEmail(user.getEmail(), subject, msg)) {

			logger.info("The password was changed and was sent via email!");
			return new ResponseMessage().getMessageJSON(ResponseValues.RECOVERYSUCCESS);
		} else {

			logger.warn(
					"No account with the provided email was found or the specified questions and answers are incorrect!");
			return new ResponseMessage().getMessageJSON(ResponseValues.RECOVERYINCORRECTEMAIL);
		}
	}
}
