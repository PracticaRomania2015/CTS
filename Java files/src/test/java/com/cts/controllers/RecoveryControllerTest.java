package com.cts.controllers;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.RecoveryResponse;
import com.cts.communication.ResponseValues;
import com.cts.entities.User;

public class RecoveryControllerTest {

	private static RecoveryController recoveryController;
	private static User user;
	
	@BeforeClass
	public static void beforeClass() {

		recoveryController = new RecoveryController();
		user = new User();
	}
	
	@Test
	public void testWithInvalidEmail() {
		
		user.setEmail("test@test.com");
		assertEquals(new RecoveryResponse().getMessageJson(ResponseValues.RECOVERYINCORRECTEMAIL), recoveryController.recoveryPassword(user));
	}

}
