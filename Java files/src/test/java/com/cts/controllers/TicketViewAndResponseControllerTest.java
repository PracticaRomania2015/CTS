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
		ticket.getComments().add(comment);
		assertEquals(new TicketError().getErrorJson(4), ticketViewAndResponseController.addComment(ticket));
		ticket = new Ticket();
		comment = new TicketComment();
		comment.setComment("");
		ticket.getComments().add(comment);
		assertEquals(new TicketError().getErrorJson(5), ticketViewAndResponseController.addComment(ticket));
		ticket = new Ticket();
		comment = new TicketComment();
		comment.setComment(null);
		ticket.getComments().add(comment);
		assertEquals(new TicketError().getErrorJson(5), ticketViewAndResponseController.addComment(ticket));
		assertEquals(new TicketError().getErrorJson(5), ticketViewAndResponseController.addComment(null));
	}
}
