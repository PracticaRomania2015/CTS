package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.User;
import com.cts.errors.LoginError;
import com.cts.errors.RegisterError;

public class RegisterControllerTest {

	private static RegisterController registerController;
	private static LoginController loginController;
	private static User testUser;
	private static ObjectMapper objectMapper;
	private static UserDAOInterface userDAO;

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
		testUser = new User();
		objectMapper = new ObjectMapper();
	}

	private void setUserParams(String firstName, String lastName, String title, String email, String password) {

		testUser.setFirstName(firstName);
		testUser.setEmail(email);
		testUser.setLastName(lastName);
		testUser.setTitle(title);
		testUser.setPassword(password);
	}

	private String generateErrorJson(int errorCode) {

		String errorMessageJson = "";
		RegisterError registerError = new RegisterError(errorCode);
		try {

			errorMessageJson = objectMapper.writeValueAsString(registerError);
		} catch (IOException e) {
		}
		return errorMessageJson;
	}

	@Test
	public void registerGoodTest() {

		setUserParams(testFirstName, testLastName, testTitle, testEmail, testPassword);
		assertEquals(generateErrorJson(8), registerController.register(testUser));
		assertEquals(generateErrorJson(9), registerController.register(testUser));
		assertNotNull("error", loginController.login(testUser));
		testUser.setPassword("a");
		assertEquals(LoginError.getDescriptionByCode(3), loginController.login(testUser));
		assertTrue(userDAO.deleteAccount(testUser));
	}

	@Test
	public void registerWithWrongEmail() {

		setUserParams(testFirstName, testLastName, testTitle, wrongEmail, testPassword);
		assertEquals(generateErrorJson(5), registerController.register(testUser));
	}

	@Test
	public void registerGET() {

		assertEquals("index", registerController.firstThingCalled());
	}

	@Test
	public void registerWithNullParamsTest() {

		setUserParams(nullFirstName, nullLastName, nullTitle, nullEmail, nullPassword);
		assertEquals(generateErrorJson(7), registerController.register(testUser));
	}

	@Test
	public void registerWithEmptyParamsTest() {

		setUserParams(emptyFirstName, emptyLastName, emptyTitle, emptyEmail, emptyPassword);
		assertEquals(generateErrorJson(6), registerController.register(testUser));
	}
}