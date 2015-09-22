package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.LoginResponse;
import com.cts.communication.RecoveryResponse;
import com.cts.communication.RegisterResponse;
import com.cts.communication.ResponseValues;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.User;

public class RegisterControllerTest {

	private static RegisterController registerController;
	private static LoginController loginController;
	private static RecoveryController recoveryPasswordController;
	private static User testUser;
	private static UserDAOInterface userDAO;
	private static FirstCallController initializer;

	private static final String emptyEmail = "";
	private static final String nullEmail = null;
	private static final String testEmail = "test@gmail.com";
	private static final String wrongEmail = "test@yahoo.com";

	private static final String emptyFirstName = "";
	private static final String nullFirstName = null;
	private static final String testFirstName = "testfirstname";

	private static final String emptyLastName = "";
	private static final String nullLastName = null;
	private static final String testLastName = "testlastname";

	private static final String emptyPassword = "";
	private static final String nullPassword = null;
	private static final String testPassword = "testpassword";

	private static final String emptyTitle = "";
	private static final String nullTitle = null;
	private static final String testTitle = "Mr.";

	@BeforeClass
	public static void initialize() {

		userDAO = new UserDAO();
		registerController = new RegisterController();
		loginController = new LoginController();
		recoveryPasswordController = new RecoveryController();
		initializer = new FirstCallController();
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
	public void registerGoodTest() {

		setUserParams(testFirstName, testLastName, testTitle, testEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.REGISTERSUCCESS), registerController.register(testUser));
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.REGISTEREXISTINGEMAIL), registerController.register(testUser));
		testUser.setPassword(testPassword);
		assertNotNull(new LoginResponse().getMessageJson(ResponseValues.LOGININVALIDCREDENTIALS), loginController.login(testUser));
		testUser.setPassword("a");
		assertEquals(new LoginResponse().getMessageJson(ResponseValues.LOGININVALIDCREDENTIALS), loginController.login(testUser));
		assertEquals(new RecoveryResponse().getMessageJson(ResponseValues.RECOVERYSUCCESS), recoveryPasswordController.recoveryPassword(testUser));
		assertTrue(userDAO.deleteAccount(testUser));
	}

	@Test
	public void registerWithWrongEmail() {

		setUserParams(testFirstName, testLastName, testTitle, wrongEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.INVALIDEMAILFORMAT), registerController.register(testUser));
	}

	@Test
	public void registerGET() {

		assertEquals("index", initializer.firstThingCalled());
	}
	
	@Test
	public void registerWithNullTitle() {
		setUserParams(testFirstName, testLastName, nullTitle, testEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYTITLE), registerController.register(testUser));
	}
	
	@Test
	public void registerWithEmptyTitle() {
		setUserParams(testFirstName, testLastName, emptyTitle, testEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYTITLE), registerController.register(testUser));
	}

	@Test
	public void registerWithNullFirstName() {
		setUserParams(nullFirstName, testLastName, testTitle, testEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYFIRSTNAME), registerController.register(testUser));
	}
	
	@Test
	public void registerWithEmptyFirstName() {
		setUserParams(emptyFirstName, testLastName, testTitle, testEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYFIRSTNAME), registerController.register(testUser));
	}
	
	@Test
	public void registerWithNullLastName() {
		setUserParams(testFirstName, nullLastName, testTitle, testEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYLASTNAME), registerController.register(testUser));
	}
	
	@Test
	public void registerWithEmptyLastName() {
		setUserParams(testFirstName, emptyLastName, testTitle, testEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYLASTNAME), registerController.register(testUser));
	}
	
	@Test
	public void registerWithNullEmail() {
		setUserParams(testFirstName, testLastName, testTitle, nullEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYEMAIL), registerController.register(testUser));
	}
	
	@Test
	public void registerWithEmptyEmail() {
		setUserParams(testFirstName, testLastName, testTitle, emptyEmail, testPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYEMAIL), registerController.register(testUser));
	}
	
	@Test
	public void registerWithNullPassword() {
		setUserParams(testFirstName, testLastName, testTitle, testEmail, nullPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYPASSWORD), registerController.register(testUser));
	}
	
	@Test
	public void registerWithEmptyPassword() {
		setUserParams(testFirstName, testLastName, testTitle, testEmail, emptyPassword);
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.EMPTYPASSWORD), registerController.register(testUser));
	}
}