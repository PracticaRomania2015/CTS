package com.cts.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.communication.ResponseValues;
import com.cts.communication.UserPropertiesResponse;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.UserForUpdate;
import com.cts.utils.HashUtil;

@Controller
public class UserPropertiesController {

	private static Logger logger = Logger.getLogger(UserPropertiesController.class.getName());

	/**
	 * @param user
	 * @return updated user object in json format.
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public @ResponseBody String updateUserPersonalData(@RequestBody UserForUpdate user) {
		
		// UserId validation
		if (Integer.valueOf(user.getUserId()) == null){
			
			logger.info("Invalid User ID.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.ERROR);
		}
			
		if (Integer.valueOf(user.getUserId()).equals("")){
			
			logger.info("Invalid User ID.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.ERROR);
		}
		
		// User title validation
		if (user.getTitle() == null) {
							
			logger.info("Title is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.EMPTYTITLE);
		}
		
		if (user.getTitle().equals("")) {
			
			logger.info("Title is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.EMPTYTITLE);
		}
		
		// User first name validation
		if (user.getFirstName() == null) {
			
			logger.info("First Name is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.EMPTYFIRSTNAME);
		}
		
		if (user.getFirstName().equals("")) {
			
			logger.info("First Name is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.EMPTYFIRSTNAME);
		}
		
		// User last name validation
		if (user.getLastName() == null) {
			
			logger.info("Last Name is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.EMPTYLASTNAME);
		}
		
		if (user.getLastName().equals("")) {
			
			logger.info("Last Name is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.EMPTYLASTNAME);
		}
		
		// User email validation
		if (user.getEmail() == null) {
			
			logger.info("Email is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.EMPTYEMAIL);
		}
		
		if (user.getEmail().equals("")) {
			
			logger.info("Email is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.EMPTYEMAIL);
		}

		// User old password validation
		if (user.getOldPassword() == null) {
					
			logger.info("Old password is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.UPDATEUSEREMPTYOLDPASSWORD);
		}
		
		if (user.getOldPassword().equals("")) {
			
			logger.info("Old password is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.UPDATEUSEREMPTYOLDPASSWORD);
		}
		
		// User new password validation
		if (user.getPassword() == null) {
			
			logger.info("New password is null or empty.");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.EMPTYPASSWORD);
		}
		
		if (!user.getPassword().equals("")){
			
			if (user.getOldPassword() != user.getPassword()){
				
				logger.info("Passwords not matching.");
				return new UserPropertiesResponse().getMessageJson(ResponseValues.UPDATEUSERPASSWORDSNOTMATCHING);
			}
		}
		
		user.setOldPassword(HashUtil.getHash((user.getOldPassword())));
		user.setPassword(HashUtil.getHash((user.getPassword())));

		UserDAOInterface userDAO = new UserDAO();
		if (userDAO.updateUserPersonalData(user)) {
		
			logger.info("Account updated successfully!");
			return new UserPropertiesResponse().getMessageJson(ResponseValues.UPDATEUSERSUCCESS);
		}
		
		logger.info("ERROR: Database error when trying to update user!");
		return new UserPropertiesResponse().getMessageJson(ResponseValues.DBERROR);

	}
}