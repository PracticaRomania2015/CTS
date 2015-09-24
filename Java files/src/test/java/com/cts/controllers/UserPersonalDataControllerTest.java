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
import com.cts.communication.UserPersonalDataResponse;
import com.cts.dao.UserDAO;
import com.cts.entities.UserForUpdate;

public class UserPersonalDataControllerTest {

	private static LoginController loginController;
	private static RegisterController registerController;
	private static UserPersonalDataController userPersonalDataController;
	private static UserDAO userDAO;
	private static UserForUpdate validUser, testUser;
	
	private static final String empty = "";
	
	private static final String validTitle = "Mr.";
	private static final String validFirstName = "testFirstName";
	private static final String validLastName = "testLastName";
	private static final String validEmail = "test@gmail.com";
	private static final String validPassword = "testPassword";
	
	private static final String validNewTitle = "Ms.";
	private static final String validNewFirstName = "testNewFirstName";
	private static final String validNewLastName = "testNewLastName";
	private static final String validNewEmail = "testNew@gmail.com";
	private static final String validNewPassword = "testNewPassword";
	
	private static final int invalidUserId = -1;
	private static final String wrongPassword = "wrongTestPassword";

	@BeforeClass
	public static void beforeClass() {
		
		loginController = new LoginController();
		registerController = new RegisterController();
		userPersonalDataController = new UserPersonalDataController();
		userDAO = new UserDAO();
		
		//valid account
		validUser.setTitle(validTitle);
		validUser.setFirstName(validFirstName);
		validUser.setLastName(validLastName);
		validUser.setEmail(validEmail);
		validUser.setPassword(validPassword);
		assertEquals(new RegisterResponse().getMessageJSON(ResponseValues.REGISTERSUCCESS), registerController.register(validUser));
		validUser.setPassword(validPassword);
		assertFalse(new LoginResponse().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS), new LoginResponse().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS).equals(loginController.login(validUser)));
		validUser.setOldPassword(validPassword);
	}
	
	@AfterClass
	public static void afterClass(){
		
		//delete valid account
		assertTrue(userDAO.deleteAccount(validUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithEmptyUserId() {

		testUser = validUser;
		testUser.setUserId(invalidUserId);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.DBERROR), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithInvalidUserId() {

		testUser = validUser;
		testUser.setUserId(invalidUserId);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.DBERROR), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithNullTitle() {

		testUser = validUser;
		testUser.setTitle(null);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYTITLE), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithEmptyTitle() {

		testUser = validUser;
		testUser.setTitle(empty);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYTITLE), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithNullFirstName() {

		testUser = validUser;
		testUser.setFirstName(null);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYFIRSTNAME), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithEmptyFirstName() {

		testUser = validUser;
		testUser.setFirstName(empty);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYFIRSTNAME), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithNullLastName() {

		testUser = validUser;
		testUser.setLastName(null);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYLASTNAME), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithEmptyLastName() {

		testUser = validUser;
		testUser.setLastName(empty);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYLASTNAME), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithNullEmail() {

		testUser = validUser;
		testUser.setEmail(null);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYEMAIL), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithEmptyEmail() {

		testUser = validUser;
		testUser.setEmail(empty);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYEMAIL), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithNullOldPassword() {

		testUser = validUser;
		testUser.setOldPassword(null);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.UPDATEUSEREMPTYOLDPASSWORD), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithEmptyOldPassword() {

		testUser = validUser;
		testUser.setOldPassword(empty);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.UPDATEUSEREMPTYOLDPASSWORD), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithEmptyNewPassword() {

		testUser = validUser;
		testUser.setPassword(empty);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.EMPTYPASSWORD), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithNewPasswordNotMathing() {

		testUser = validUser;
		testUser.setPassword(wrongPassword);
		assertEquals(new UserPersonalDataResponse().getMessageJSON(ResponseValues.UPDATEUSERPASSWORDSNOTMATCHING), userPersonalDataController.updateUserPersonalData(testUser));
	}
	
	@Test
	public void testUpdateUserPersonalDataWithValidData() {

		validUser.setTitle(validNewTitle);
		validUser.setFirstName(validNewFirstName);
		validUser.setLastName(validNewLastName);
		validUser.setEmail(validNewEmail);
		validUser.setPassword(validNewPassword);
		validUser.setOldPassword(validPassword);
		assertFalse(new UserPersonalDataResponse().getMessageJSON(ResponseValues.DBERROR), new UserPersonalDataResponse().getMessageJSON(ResponseValues.DBERROR).equals(userPersonalDataController.updateUserPersonalData(validUser)));
	}
}