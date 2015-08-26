package com.cts.controllers;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import com.cts.entities.User;

public class RecoveryPasswordControllerTest {

	private static RecoveryPasswordController recoveryPasswordController;
	private static User user;
	
	@BeforeClass
	public static void beforeClass() {

		recoveryPasswordController = new RecoveryPasswordController();
		user = new User();
	}
	
	@Test
	public void testWithInvalidEmail() {
		
		user.setEmail("test@test.com");
		assertNotNull("The specified email is incorrect!", recoveryPasswordController.recoveryPassword(user));
	}

}