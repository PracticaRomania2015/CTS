package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.ResponseMessage;
import com.cts.communication.ResponseValues;
import com.cts.dao.UserDAO;
import com.cts.entities.User;

public class RecoveryControllerTest {

	private static RecoveryController recoveryController;
	private static LoginController loginController;
	private static RegisterController registerController;
	private static UserDAO userDAO;
	private static User validUser;
	
	private static final String empty = "";
	
	private static final String validTitle = "Mr.";
	private static final String validFirstName = "testFirstName";
	private static final String validLastName = "testLastName";
	private static final String validEmail = "test@gmail.com";
	private static final String validPassword = "testPassword";
	
	private static final String invalidEmail = "test@yahoo.com";
	
	@BeforeClass
	public static void beforeClass() {

		registerController = new RegisterController();
		loginController = new LoginController();
		recoveryController = new RecoveryController();
		userDAO = new UserDAO();
		validUser = new User();
		//valid account
		validUser.setTitle(validTitle);
		validUser.setFirstName(validFirstName);
		validUser.setLastName(validLastName);
		validUser.setEmail(validEmail);
		validUser.setPassword(validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.REGISTERSUCCESS), registerController.register(validUser));
		validUser.setPassword(validPassword);
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS), new ResponseMessage().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS).equals(loginController.login(validUser)));
	}
	
	@AfterClass
	public static void afterClass(){
		//delete valid account
		assertTrue(userDAO.deleteAccount(validUser));
	}
	
	@Test
	public void testRecoveryPasswordWithNullEmail() {
		
		validUser.setEmail(null);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL), recoveryController.recoveryPassword(validUser));
	}
	
	@Test
	public void testRecoveryPasswordWithEmptyEmail() {
		
		validUser.setEmail(empty);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL), recoveryController.recoveryPassword(validUser));
	}
	
	@Test
	public void testRecoveryPasswordWithInvalidEmail() {
		
		validUser.setEmail(invalidEmail);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.RECOVERYINCORRECTEMAIL), recoveryController.recoveryPassword(validUser));
	}
	
	@Test
	public void testRecoveryPasswordWithValidData() {
		
		validUser.setEmail(validEmail);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.RECOVERYSUCCESS), recoveryController.recoveryPassword(validUser));
	}

}
