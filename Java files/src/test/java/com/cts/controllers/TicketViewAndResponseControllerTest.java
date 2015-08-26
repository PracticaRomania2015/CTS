package com.cts.controllers;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.entities.Ticket;

public class TicketViewAndResponseControllerTest {

	private static TicketViewAndResponseController ticketViewAndResponseController;
	
	@BeforeClass
	public static void beforeClass() {
		
		ticketViewAndResponseController = new TicketViewAndResponseController();
	}
	
	@Test
	public void testViewTicketComments() {
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(1);
		assertNotNull("json error", ticketViewAndResponseController.viewTicketComments(ticket));
	}

	
	@Test
	public void testAddComment() {
		
		
	}
}
