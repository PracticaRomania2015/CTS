package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.LoginResponse;
import com.cts.communication.ResponseValues;
import com.cts.entities.User;

public class LoginControllerTest {

	private static LoginController loginController;
	private static User testUser;

	private static final String emptyEmail = "";
	private static final String nullEmail = null;
	private static final String testEmail = "test";

	private static final String emptyPassword = "";
	private static final String nullPassword = null;
	private static final String testPassword = "test";

	@BeforeClass
	public static void beforeClass() {

		loginController = new LoginController();
		testUser = new User();
	}

	@Test
	public void testWithNullEmail() {

		testUser.setEmail(nullEmail);
		testUser.setPassword(testPassword);
		assertEquals(new LoginResponse().getMessageJson(ResponseValues.EMPTYEMAILFIELD), loginController.login(testUser));
	}

	@Test
	public void testWithEmptyEmail() {

		testUser.setEmail(emptyEmail);
		testUser.setPassword(testPassword);
		assertEquals(new LoginResponse().getMessageJson(ResponseValues.EMPTYEMAILFIELD), loginController.login(testUser));
	}

	@Test
	public void testWithNullPassword() {

		testUser.setEmail(testEmail);
		testUser.setPassword(nullPassword);
		assertEquals(new LoginResponse().getMessageJson(ResponseValues.EMPTYPASSWORDFIELD), loginController.login(testUser));
	}

	@Test
	public void testWithEmptyPassword() {

		testUser.setEmail(testEmail);
		testUser.setPassword(emptyPassword);
		assertEquals(new LoginResponse().getMessageJson(ResponseValues.EMPTYPASSWORDFIELD), loginController.login(testUser));
	}

	@Test
	public void testUserObjectInJsonFormatMethod() {

		testUser.setEmail("sada");
		testUser.setPassword("dasda");
		assertNotNull(loginController.getUserObjectInJsonFormat(testUser));

		testUser.setEmail("");
		testUser.setPassword("");
		assertNotNull(loginController.getUserObjectInJsonFormat(testUser));

		testUser.setEmail(null);
		testUser.setPassword(null);
		assertNotNull(loginController.getUserObjectInJsonFormat(testUser));

		assertNotNull(loginController.getUserObjectInJsonFormat(null));
	}
}