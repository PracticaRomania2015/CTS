package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.ResponseMessage;
import com.cts.communication.ResponseValues;
import com.cts.dao.UserDAO;
import com.cts.entities.User;

public class RegisterControllerTest {

	private static RegisterController registerController;
	private static LoginController loginController;
	private static UserDAO userDAO;
	private static User testUser;
	
	private static final String empty = "";
	
	private static final String validTitle = "Mr.";
	private static final String validFirstName = "testFirstName";
	private static final String validLastName = "testLastName";
	private static final String validEmail = "test@gmail.com";
	private static final String validPassword = "testPassword";
	
	private static final String invalidEmail = "test@yahoo.com";

	@BeforeClass
	public static void beforeClass() {

		userDAO = new UserDAO();
		registerController = new RegisterController();
		loginController = new LoginController();
		testUser = new User();
	}

	private void setUserParams(String firstName, String lastName, String title, String email, String password) {

		testUser.setFirstName(firstName);
		testUser.setEmail(email);
		testUser.setLastName(lastName);
		testUser.setTitle(title);
		testUser.setPassword(password);
	}

	@Test
	public void testRegisterWithInvalidEmail() {

		setUserParams(validFirstName, validLastName, validTitle, invalidEmail, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.INVALIDEMAILFORMAT), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithNullTitle() {
		setUserParams(validFirstName, validLastName, null, validEmail, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYTITLE), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithEmptyTitle() {
		setUserParams(validFirstName, validLastName, empty, validEmail, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYTITLE), registerController.register(testUser));
	}

	@Test
	public void testRegisterWithNullFirstName() {
		setUserParams(null, validLastName, validTitle, validEmail, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYFIRSTNAME), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithEmptyFirstName() {
		setUserParams(empty, validLastName, validTitle, validEmail, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYFIRSTNAME), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithNullLastName() {
		setUserParams(validFirstName, null, validTitle, validEmail, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYLASTNAME), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithEmptyLastName() {
		setUserParams(validFirstName, empty, validTitle, validEmail, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYLASTNAME), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithNullEmail() {
		setUserParams(validFirstName, validLastName, validTitle, null, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithEmptyEmail() {
		setUserParams(validFirstName, validLastName, validTitle, empty, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYEMAIL), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithNullPassword() {
		setUserParams(validFirstName, validLastName, validTitle, validEmail, null);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYPASSWORD), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithEmptyPassword() {
		setUserParams(validFirstName, validLastName, validTitle, validEmail, empty);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.EMPTYPASSWORD), registerController.register(testUser));
	}
	
	@Test
	public void testRegisterWithValidData() {

		setUserParams(validFirstName, validLastName, validTitle, validEmail, validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.REGISTERSUCCESS), registerController.register(testUser));
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.REGISTEREXISTINGEMAIL), registerController.register(testUser));
		testUser.setPassword(validPassword);
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS), new ResponseMessage().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS).equals(loginController.login(testUser)));
		assertTrue(userDAO.deleteAccount(testUser));
	}
}