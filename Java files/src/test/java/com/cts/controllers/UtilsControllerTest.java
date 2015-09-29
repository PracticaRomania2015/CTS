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
import com.cts.communication.UtilsResponse;
import com.cts.dao.CategoryDAO;
import com.cts.dao.UserDAO;
import com.cts.entities.Category;
import com.cts.entities.User;

public class UtilsControllerTest {

	private static LoginController loginController;
	private static RegisterController registerController;
	private static RootManagementController rootManagementController;
	private static UtilsController utilsController;
	private static CategoryDAO categoryDAO;
	private static UserDAO userDAO;
	private static Category validCategory, validSubcategory, testCategory;
	private static User validUser;
	
	private static final String validTitle = "Mr.";
	private static final String validFirstName = "testFirstName";
	private static final String validLastName = "testLastName";
	private static final String validEmail = "test@gmail.com";
	private static final String validPassword = "testPassword";
	private static final String validCategoryName = "testCategory";
	private static final String validSubcategoryName = "testSubcategory";
	
	private static final int invalidCategoryId = 0;

	@BeforeClass
	public static void beforeClass() {
		
		loginController = new LoginController();
		registerController = new RegisterController();
		rootManagementController = new RootManagementController();
		utilsController = new UtilsController();
		categoryDAO =  new CategoryDAO();
		userDAO = new UserDAO();
		
		validCategory = new Category();
		validSubcategory = new Category();
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
		assertEquals(new CategoryResponse().getMessageJSON(ResponseValues.SUCCESS), rootManagementController.addCategory(validCategory));
		ArrayList<Category> categories = categoryDAO.getCategories();
		for(Category category : categories){
			if (category.getCategoryName().equals(validCategoryName)){
				validCategory.setCategoryId(category.getCategoryId());
			}
		}
		
		//valid subcategory
		validSubcategory.setParentCategoryId(validCategory.getCategoryId());
		validSubcategory.setCategoryName(validSubcategoryName);
		assertEquals(new CategoryResponse().getMessageJSON(ResponseValues.SUCCESS), rootManagementController.addCategory(validSubcategory));
		ArrayList<Category> subcategories = categoryDAO.getSubcategories(validCategory);
		for(Category subcategory : subcategories){
			if (subcategory.getCategoryName().equals(validSubcategoryName)){
				validSubcategory.setCategoryId(subcategory.getCategoryId());
			}
		}
	}
	
	@AfterClass
	public static void afterClass(){
		
		//delete valid account
		assertTrue(userDAO.deleteAccount(validUser));
		//delete valid subcategory
		//TODO: actual delete instead of disable
		assertTrue(categoryDAO.deleteCategory(validSubcategory));
		//delete valid category
		//TODO: actual delete instead of disable
		assertTrue(categoryDAO.deleteCategory(validCategory));
	}
	
	@Test
	public static void testGetSubcategoriesWithNullCategory() {
		
		assertEquals(new UtilsResponse().getMessageJSON(ResponseValues.UNKNOWN), utilsController.getSubcategories(null));
	}
	
	@Test
	public static void testGetSubcategoriesWithInvalidCategoryId() {
		
		testCategory = new Category();
		testCategory.setCategoryId(invalidCategoryId);
		assertEquals(new UtilsResponse().getMessageJSON(ResponseValues.DBERROR), utilsController.getSubcategories(testCategory));
	}
	
	@Test
	public static void testGetCategoriesWithValidData() {
		
		ArrayList<Category> categories = categoryDAO.getCategories();
		assertEquals(new UtilsResponse(categories).getMessageJSON(ResponseValues.SUCCESS), utilsController.getCategories());
	}
	
	@Test
	public static void testGetAdminsForCategoryWithNullCategory(){
		
		assertEquals(new UtilsResponse().getMessageJSON(ResponseValues.UNKNOWN), utilsController.getAdminsForCategory(null));
	}

	@Test
	public static void testGetAdminsForCategoryWithInvalidCategoryId() {
		
		testCategory = new Category();
		testCategory.setCategoryId(invalidCategoryId);
		assertEquals(new UtilsResponse().getMessageJSON(ResponseValues.UNKNOWN), utilsController.getAdminsForCategory(testCategory));
	}
	
	@Test
	public static void testGetSubcategoriesWithValidData() {
		
		ArrayList<Category> subcategories = new ArrayList<Category>();
		subcategories.add(validSubcategory);
		assertEquals(new UtilsResponse(subcategories).getMessageJSON(ResponseValues.SUCCESS), utilsController.getSubcategories(validCategory));
	}
	
	//TODO: add admin to category and redo test
	@Test
	public static void testGetAdminsForCategoryWithValidData() {

		ArrayList<User> admins = new ArrayList<User>();
		assertEquals(new UtilsResponse(admins).getMessageJSON(ResponseValues.SUCCESS), utilsController.getAdminsForCategory(validCategory));
	}
}