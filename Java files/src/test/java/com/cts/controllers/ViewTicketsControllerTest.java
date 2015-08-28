package com.cts.controllers;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.entities.User;
import com.cts.entities.ViewTicketsRequest;

public class ViewTicketsControllerTest {

	private static ViewTicketsController viewTicketsController;
	
	@BeforeClass
	public static void beforeClass() {
		
		viewTicketsController = new ViewTicketsController();
	}
	
	@Test
	public void testWithBadUserId() {
		
		ViewTicketsRequest viewTicketRequest = new ViewTicketsRequest();
		User user = new User();
		user.setUserId(0);
		viewTicketRequest.setUser(user);
		viewTicketRequest.setViewMyTicketsRequest(true);
		viewTicketRequest.setRequestedPageNumber(0);
		assertEquals("[]", viewTicketsController.viewTickets(viewTicketRequest));
	}
	
	@Test
	public void testWithGoodUserId() {
		
		ViewTicketsRequest viewTicketRequest = new ViewTicketsRequest();
		User user = new User();
		user.setUserId(2);
		viewTicketRequest.setUser(user);
		viewTicketRequest.setViewMyTicketsRequest(true);
		viewTicketRequest.setRequestedPageNumber(1);
		assertEquals("[]", viewTicketsController.viewTickets(viewTicketRequest));
	}
}
