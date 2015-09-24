package com.cts.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.ResponseValues;
import com.cts.communication.UserPersonalDataResponse;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.UserForUpdate;
import com.cts.utils.HashUtil;

/**
 * Handles requests for the user properties page.
 */
@Controller
public class UserPersonalDataController {

	private static Logger logger = Logger.getLogger(UserPersonalDataController.class.getName());
	
	/**
	 * Update user details
	 * 
	 * @param user User to which to update details
	 * @return JSON with success/error response.
	 */
	//TODO:
	//CHANGED FROM: /updateUser
	@RequestMapping(value = "/updateUserPersonalData", method = RequestMethod.POST)
	public @ResponseBody String updateUserPersonalData(@RequestBody UserForUpdate user) {
		
		logger.debug("Attempting to update a user's personal data.");
		
		// UserId validation			
		if (Integer.valueOf(user.getUserId()).equals("")){
			
			logger.error("User ID is empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.ERROR);
		}
		
		// User title validation
		if (user.getTitle() == null) {
							
			logger.error("Title is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYTITLE);
		}
		
		if (user.getTitle().equals("")) {
			
			logger.error("Title is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYTITLE);
		}
		
		// User first name validation
		if (user.getFirstName() == null) {
			
			logger.error("First Name is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYFIRSTNAME);
		}
		
		if (user.getFirstName().equals("")) {
			
			logger.error("First Name is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYFIRSTNAME);
		}
		
		// User last name validation
		if (user.getLastName() == null) {
			
			logger.error("Last Name is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYLASTNAME);
		}
		
		if (user.getLastName().equals("")) {
			
			logger.error("Last Name is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYLASTNAME);
		}
		
		// User email validation
		if (user.getEmail() == null) {
			
			logger.error("Email is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}
		
		if (user.getEmail().equals("")) {
			
			logger.error("Email is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYEMAIL);
		}

		// User old password validation
		if (user.getOldPassword() == null) {
					
			logger.error("Old password is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.UPDATEUSEREMPTYOLDPASSWORD);
		}
		
		if (user.getOldPassword().equals("")) {
			
			logger.error("Old password is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.UPDATEUSEREMPTYOLDPASSWORD);
		}
		
		// User new password validation
		if (user.getPassword() == null) {
			
			logger.error("New password is null or empty!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYPASSWORD);
		}
		
		if (!user.getPassword().equals("")){
			
			if (user.getOldPassword() != user.getPassword()){
				
				logger.warn("Passwords not matching!");
				return new UserPersonalDataResponse().getMessageJSON(ResponseValues.UPDATEUSERPASSWORDSNOTMATCHING);
			}
		}
		
		// Update user properties
		user.setOldPassword(HashUtil.getHash((user.getOldPassword())));
		user.setPassword(HashUtil.getHash((user.getPassword())));

		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.updateUserPersonalData(user)) {
		
			logger.info("Account updated successfully!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.UPDATEUSERSUCCESS);
		} else {
		
			logger.error("Account could not be updated!");
			return new UserPersonalDataResponse().getMessageJSON(ResponseValues.DBERROR);
		}
	}
}