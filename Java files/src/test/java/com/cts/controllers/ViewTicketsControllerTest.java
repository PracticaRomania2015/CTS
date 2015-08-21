package com.cts.controllers;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.entities.User;

public class ViewTicketsControllerTest {

	private static ViewTicketsController viewTicketsController;
	
	@BeforeClass
	public static void beforeClass() {
		
		viewTicketsController = new ViewTicketsController();
	}
	
	@Test
	public void testWithBadUserId() {
		
		User user = new User();
		user.setUserId(0);
		assertEquals("null", viewTicketsController.viewTickets(user));
	}

}
