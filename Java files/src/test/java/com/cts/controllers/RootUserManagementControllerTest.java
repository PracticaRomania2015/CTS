package com.cts.controllers;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.entities.UserStatus;
import com.cts.entities.ViewUsersRequest;

public class RootUserManagementControllerTest {

	private static RootUserManagementController RUMC;
	
	@BeforeClass
	public static void beforeClass() {
		
		RUMC = new RootUserManagementController();
	}
	
	@Test
	public void testReturnValueOfGetUsers() {
		
		ViewUsersRequest viewUsersRequest = new ViewUsersRequest();
		viewUsersRequest.setRequestedPageNumber(1);
		viewUsersRequest.setUsersPerPage(5);
		viewUsersRequest.setTextToSearch("");
		viewUsersRequest.setSearchType("");
		viewUsersRequest.setSortType("");
		viewUsersRequest.setSearchASC(true);
		System.out.println("User list: " + RUMC.viewUsers(viewUsersRequest));
	}
	
	@Test
	public void testReturnValueOfGetAdminStatus() {
		
		UserStatus userStatus = new UserStatus();
		userStatus.setUserId(1);
		System.out.println("User rights: " + RUMC.getUserAdminStatus(userStatus));
	}
}