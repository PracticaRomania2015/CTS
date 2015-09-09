package com.cts.controllers;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.RegisterResponse;
import com.cts.communication.ResponseValues;
import com.cts.communication.TicketResponse;
import com.cts.dao.TicketDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.Ticket;
import com.cts.entities.TicketComment;
import com.cts.entities.User;

public class TicketViewAndResponseControllerTest {

	private static TicketViewAndResponseController ticketViewAndResponseController;
	private static RegisterController registerController;
	private static UserDAOInterface userDAO;
	private static TicketDAOInterface ticketDAO;

	@BeforeClass
	public static void beforeClass() {

		ticketViewAndResponseController = new TicketViewAndResponseController();
		registerController = new RegisterController();
		userDAO = new UserDAO();
		ticketDAO = new TicketDAO();
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
	public void testAssignAdminToTicket() {
		
		User testUser = new User();
		testUser.setFirstName("test");
		testUser.setEmail("testtesttest@gmail.com");
		testUser.setLastName("test");
		testUser.setTitle("Mr.");
		testUser.setPassword("testtest");
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.REGISTERSUCCESS), registerController.register(testUser));
		assertTrue(userDAO.validateLogin(testUser));
		Ticket ticket = new Ticket();
		ticket.setAssignedToUser(testUser);
		assertTrue(ticketDAO.assignTicket(ticket));
		assertTrue(ticketDAO.deleteTicket(ticket));
		assertTrue(userDAO.deleteAccount(testUser));
	}
	
	@Test
	public void testGetAdminsForCategory() {
		
		Category category = new Category();
		category.setCategoryId(1);
		assertNotNull(new TicketResponse().getMessageJson(ResponseValues.UNKNOWN), ticketViewAndResponseController.getAdminsForCategory(category));
	}
}
