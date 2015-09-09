package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.ResponseValues;
import com.cts.communication.TicketResponse;
import com.cts.entities.Category;
import com.cts.entities.Ticket;
import com.cts.entities.TicketComment;

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
		assertNotNull(new TicketResponse().getMessageJson(ResponseValues.UNKNOWN), ticketViewAndResponseController.viewTicketComments(ticket));
	}

	@Test
	public void testAddComment() {

		Ticket ticket = new Ticket();
		TicketComment comment = new TicketComment();
		comment.setComment("test");
		ticket.getComments().add(comment);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.DBERROR), ticketViewAndResponseController.addComment(ticket));
		ticket = new Ticket();
		comment = new TicketComment();
		comment.setComment("");
		ticket.getComments().add(comment);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCOMMENTFIELD), ticketViewAndResponseController.addComment(ticket));
		ticket = new Ticket();
		comment = new TicketComment();
		comment.setComment(null);
		ticket.getComments().add(comment);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCOMMENTFIELD), ticketViewAndResponseController.addComment(ticket));
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYCOMMENTFIELD), ticketViewAndResponseController.addComment(null));
	}
	
	@Test
	public void testAssignTicketWithNullTicket() {
		
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.ERROR), ticketViewAndResponseController.assignTicket(null));
	}
	
	@Test
	public void testAssignTicketWithBadInfo() {
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(-1);
		ticket.getAssignedToUser().setUserId(-1);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.DBERROR), ticketViewAndResponseController.assignTicket(ticket));
	}
	
	@Test
	public void testCloseTicketWithNullTicket() {
		
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.ERROR), ticketViewAndResponseController.closeTicket(null));
	}
	
	@Test
	public void testCloseTicketWithBadInfo() {
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(-1);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.DBERROR), ticketViewAndResponseController.closeTicket(ticket));
	}
		
	@Test
	public void testGetAdminsForCategory() {
		
		Category category = new Category();
		category.setCategoryId(1);
		assertNotNull(new TicketResponse().getMessageJson(ResponseValues.UNKNOWN), ticketViewAndResponseController.getAdminsForCategory(category));
	}
}
