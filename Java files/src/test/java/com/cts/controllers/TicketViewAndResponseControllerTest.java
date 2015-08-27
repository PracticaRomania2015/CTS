package com.cts.controllers;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.TicketError;
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
		assertNotNull("json error", ticketViewAndResponseController.viewTicketComments(ticket));
	}

	@Test
	public void testAddComment() {

		Ticket ticket = new Ticket();
		TicketComment comment = new TicketComment();
		comment.setComment("test");
		ticket.setNewTicketComment(comment);
		assertEquals(new TicketError().getErrorJson(4), ticketViewAndResponseController.addComment(ticket));
		comment.setComment("");
		assertEquals(new TicketError().getErrorJson(5), ticketViewAndResponseController.addComment(ticket));
		comment.setComment(null);
		assertEquals(new TicketError().getErrorJson(5), ticketViewAndResponseController.addComment(ticket));
		ticket.setNewTicketComment(null);
		assertEquals(new TicketError().getErrorJson(5), ticketViewAndResponseController.addComment(ticket));
		assertEquals(new TicketError().getErrorJson(5), ticketViewAndResponseController.addComment(null));
	}
}
