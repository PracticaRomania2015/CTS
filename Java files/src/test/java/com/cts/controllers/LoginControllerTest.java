package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.LoginResponse;
import com.cts.communication.RegisterResponse;
import com.cts.communication.ResponseValues;
import com.cts.dao.UserDAO;
import com.cts.entities.User;

public class LoginControllerTest {

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
	
	private static final String inexistentEmail = "inexistentAccount@gmail.com";

	@BeforeClass
	public static void beforeClass() {

		registerController = new RegisterController();
		loginController = new LoginController();
		userDAO = new UserDAO();
		validUser = new User();
		//valid account
		validUser.setTitle(validTitle);
		validUser.setFirstName(validFirstName);
		validUser.setLastName(validLastName);
		validUser.setEmail(validEmail);
		validUser.setPassword(validPassword);
		assertEquals(new RegisterResponse().getMessageJSON(ResponseValues.REGISTERSUCCESS), registerController.register(validUser));
	}
	
	@AfterClass
	public static void afterClass(){
		//delete valid account
		assertTrue(userDAO.deleteAccount(validUser));
	}
	
	@Test
	public void testLoginWithNullEmail() {

		validUser.setEmail(null);
		validUser.setPassword(validPassword);
		assertEquals(new LoginResponse().getMessageJSON(ResponseValues.EMPTYEMAIL), loginController.login(validUser));
	}
	
	@Test
	public void testLoginWithNullPassword() {

		validUser.setEmail(validEmail);
		validUser.setPassword(null);
		assertEquals(new LoginResponse().getMessageJSON(ResponseValues.EMPTYPASSWORD), loginController.login(validUser));
	}

	@Test
	public void testLoginWithEmptyEmail() {

		validUser.setEmail(empty);
		validUser.setPassword(validPassword);
		assertEquals(new LoginResponse().getMessageJSON(ResponseValues.EMPTYEMAIL), loginController.login(validUser));
	}

	@Test
	public void testLoginWithEmptyPassword() {

		validUser.setEmail(validEmail);
		validUser.setPassword(empty);
		assertEquals(new LoginResponse().getMessageJSON(ResponseValues.EMPTYPASSWORD), loginController.login(validUser));
	}
	
	@Test
	public void testLoginWithInvalidData() {
		
		validUser.setEmail(inexistentEmail);
		validUser.setPassword(validPassword);
		assertEquals(new LoginResponse().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS), loginController.login(validUser));
	}
	
	@Test
	public void testLoginWithValidData() {
		
		validUser.setPassword(validPassword);
		assertFalse(new LoginResponse().getMessageJSON(ResponseValues.EMPTYEMAIL), new LoginResponse().getMessageJSON(ResponseValues.EMPTYEMAIL).equals(loginController.login(validUser)));
		validUser.setPassword(validPassword);
		assertFalse(new LoginResponse().getMessageJSON(ResponseValues.EMPTYPASSWORD), new LoginResponse().getMessageJSON(ResponseValues.EMPTYPASSWORD).equals(loginController.login(validUser)));
		validUser.setPassword(validPassword);
		assertFalse(new LoginResponse().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS), new LoginResponse().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS).equals(loginController.login(validUser)));
	}
}