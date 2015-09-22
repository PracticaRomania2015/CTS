package com.cts.controllers;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.entities.User;
import com.cts.entities.ViewTicketsRequest;

public class ViewTicketsControllerTest {

	private static TicketsController ticketsController;
	
	@BeforeClass
	public static void beforeClass() {
		
		ticketsController = new TicketsController();
	}
	
	@Test
	public void testWithBadUserId() {
		
		ViewTicketsRequest viewTicketRequest = new ViewTicketsRequest();
		User user = new User();
		user.setUserId(0);
		viewTicketRequest.setUser(user);
		viewTicketRequest.setTypeOfRequest(0);
		viewTicketRequest.setRequestedPageNumber(1);
		viewTicketRequest.setTicketsPerPage(1);
		assertEquals("{\"totalNumberOfPages\":1,\"tickets\":[]}", ticketsController.viewTickets(viewTicketRequest));
	}
}
