package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.CategoryResponse;
import com.cts.communication.LoginResponse;
import com.cts.communication.RegisterResponse;
import com.cts.communication.ResponseValues;
import com.cts.communication.UserRightsResponse;
import com.cts.communication.UtilsResponse;
import com.cts.dao.CategoryDAO;
import com.cts.dao.UserDAO;
import com.cts.entities.Category;
import com.cts.entities.Ticket;
import com.cts.entities.User;
import com.cts.entities.UserRight;
import com.cts.entities.UserStatus;
import com.cts.entities.ViewUsersRequest;

public class RootManagementControllerTest {

	private static LoginController loginController;
	private static RegisterController registerController;
	private static RootManagementController rootManagementController;
	private static UtilsController utilsController;
	private static CategoryDAO categoryDAO;
	private static UserDAO userDAO;
	private static Category validCategory;
	private static UserStatus validUserStatus, testUserStatus;
	private static UserRight validUserRight;
	private static User validUser;
	
	private static final String validTitle = "Mr.";
	private static final String validFirstName = "testFirstName";
	private static final String validLastName = "testLastName";
	private static final String validEmail = "test@gmail.com";
	private static final String validPassword = "testPassword";
	private static final String validCategoryName = "testCategory";
	
	@BeforeClass
	public static void beforeClass() {
		
		loginController = new LoginController();
		registerController = new RegisterController();
		rootManagementController = new RootManagementController();
		utilsController = new UtilsController();
		categoryDAO =  new CategoryDAO();
		userDAO = new UserDAO();
		
		validCategory = new Category();
		validUserStatus = new UserStatus();
		validUserRight = new UserRight();
		validUser = new User();
		
		//valid account
		validUser.setTitle(validTitle);
		validUser.setFirstName(validFirstName);
		validUser.setLastName(validLastName);
		validUser.setEmail(validEmail);
		validUser.setPassword(validPassword);
		assertEquals(new RegisterResponse().getMessageJSON(ResponseValues.REGISTERSUCCESS), registerController.register(validUser));
		validUser.setPassword(validPassword);
		assertFalse(new LoginResponse().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS), new LoginResponse().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS).equals(loginController.login(validUser)));
		
		//valid category
		validCategory.setCategoryName(validCategoryName);
		assertEquals(new CategoryResponse().getMessageJSON(ResponseValues.SUCCESS), rootManagementController.addCateg(validCategory));
		ArrayList<Category> categories = categoryDAO.getCategories();
		for(Category category : categories){
			if (category.getCategoryName().equals(validCategoryName)){
				validCategory.setCategoryId(category.getCategoryId());
			}
		}
		
		//valid userRight
		validUserRight.setCategory(validCategory);
		validUserRight.setAdminStatus(true);
		
		//valid userStatus
		validUserStatus.setUserId(validUser.getUserId());
	}
	
	@AfterClass
	public static void afterClass(){
		
		//delete valid account
		assertTrue(userDAO.deleteAccount(validUser));
		//delete valid category
		//TODO: actual delete instead of disable
		assertTrue(categoryDAO.deleteCategory(validCategory));
	}
	
	@Test
	public void testGetUsersWithValidData() {

		ViewUsersRequest viewUsersRequest = new ViewUsersRequest();
		viewUsersRequest.setRequestedPageNumber(1);
		viewUsersRequest.setUsersPerPage(10);
		viewUsersRequest.setTextToSearch("");
		viewUsersRequest.setSearchType("");
		viewUsersRequest.setSearchASC(true);
		
		StringBuilder totalNumberOfPages = new StringBuilder("1");
		ArrayList<Object> output = new ArrayList<Object>();
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		output.add(totalNumberOfPages);
		output.add(tickets);
		assertEquals(new UserRightsResponse(output).getMessageJSON(ResponseValues.SUCCESS), rootManagementController.getUsers(viewUsersRequest));
	}
	
	//TODO: needs DAO implementation
	@Test
	public void testSetUserRightsWithValidData() {
		
		ArrayList<UserRight> categoryAdminRights = new ArrayList<UserRight>();
		categoryAdminRights.add(validUserRight);
		
		validUserStatus.setSysAdmin(true);
		validUserStatus.setCategoryAdminRights(categoryAdminRights);
		
		rootManagementController.setUserRights(validUserStatus);
	}
	
	//TODO: needs DAO implementation to check against
	@Test
	public void testGetUserRightsWithValidData() {
		
		ArrayList<UserRight> categoryAdminRights = new ArrayList<UserRight>();
		categoryAdminRights.add(validUserRight);
		ArrayList<Category> categories = categoryDAO.getCategories();
		assertEquals(new UtilsResponse(categories).getMessageJSON(ResponseValues.SUCCESS), utilsController.getCategories());
		for (Category category : categories){
			
			validUserRight.setCategory(category);
			validUserRight.setAdminStatus(false);
			categoryAdminRights.add(validUserRight);
		}
		
		testUserStatus = new UserStatus();
		testUserStatus.setUserId(validUser.getUserId());
		testUserStatus.setSysAdmin(true);
		testUserStatus.setCategoryAdminRights(categoryAdminRights);
		
		assertEquals(new UserRightsResponse(testUserStatus).getMessageJSON(ResponseValues.SUCCESS), rootManagementController.getUserRights(validUserStatus));
	}
}