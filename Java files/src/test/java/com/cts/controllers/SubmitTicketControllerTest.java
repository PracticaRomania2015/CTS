package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import com.cts.dao.TicketDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.Ticket;
import com.cts.entities.TicketComment;
import com.cts.entities.User;
import com.cts.errors.RegisterError;
import com.cts.errors.SubmitTicketError;

public class SubmitTicketControllerTest {

	private static Ticket ticket;
	private static SubmitTicketController submitTicketController;
	private static TicketViewAndResponseController ticketViewAndResponseController;
	private static RegisterController registerController;
	private static LoginController loginController;
	private static TicketDAOInterface ticketDAO;
	private static UserDAOInterface userDAO;
	private static ObjectMapper objectMapper;

	private static final String nullSubject = null;
	private static final String emptySubject = "";
	private static final String testSubject = "test";

	private static final String nullComment = null;
	private static final String emptyComment = "";
	private static final String testComment = "test";

	private static ArrayList<TicketComment> comments;
	private static TicketComment testTicketComment;

	private static final String testFilePath = "test";

	private String getTicketInJsonFormat(Ticket ticket) {

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(ticket);
		} catch (IOException e) {
			return null;
		}
	}

	@Before
	public void beforeTest() {

		userDAO = new UserDAO();
		ticketDAO = new TicketDAO();
		submitTicketController = new SubmitTicketController();
		loginController = new LoginController();
		ticketViewAndResponseController = new TicketViewAndResponseController();
		registerController = new RegisterController();
		ticket = new Ticket();
		comments = new ArrayList<TicketComment>();
		testTicketComment = new TicketComment();
		testTicketComment.setDateTime(new Timestamp(0));
		testTicketComment.setFilePath(testFilePath);
		testTicketComment.setUserId(0);
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testWithNullSubject() {

		testTicketComment.setComment(testComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(nullSubject);
		ticket.setCategoryId(1);
		ticket.setComments(comments);
		assertEquals(SubmitTicketError.getDescriptionByCode(1), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithEmptySubject() {

		testTicketComment.setComment(testComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(emptySubject);
		ticket.setCategoryId(1);
		ticket.setComments(comments);
		assertEquals(SubmitTicketError.getDescriptionByCode(1), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithBadCategory() {

		testTicketComment.setComment(testComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		ticket.setCategoryId(0);
		ticket.setComments(comments);
		assertEquals(SubmitTicketError.getDescriptionByCode(2), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithNullComment() {

		testTicketComment.setComment(nullComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		ticket.setCategoryId(1);
		ticket.setComments(comments);
		assertEquals(SubmitTicketError.getDescriptionByCode(3), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithEmptyComment() {

		testTicketComment.setComment(emptyComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		ticket.setCategoryId(1);
		ticket.setComments(comments);
		assertEquals(SubmitTicketError.getDescriptionByCode(3), submitTicketController.submitTicket(ticket));
	}

	private String generateErrorJson(int errorCode) {

		String errorMessageJson = "";
		RegisterError registerError = new RegisterError(errorCode);
		try {

			errorMessageJson = objectMapper.writeValueAsString(registerError);
		} catch (IOException e) {
		}
		return errorMessageJson;
	}

	@Test
	public void testWithValidInformation() {

		User testUser = new User();
		testUser.setFirstName("test");
		testUser.setEmail("testtesttest@gmail.com");
		testUser.setLastName("test");
		testUser.setTitle("test");
		testUser.setPassword("test");
		assertEquals(generateErrorJson(8), registerController.register(testUser));
		testUser.setPassword("test");
		assertNotNull("error", loginController.login(testUser));
		testTicketComment.setUserId(testUser.getUserId());
		testTicketComment.setComment(testComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		ticket.setCategoryId(1);
		ticket.setComments(comments);
		String ticketJson = submitTicketController.submitTicket(ticket);
		assertEquals(getTicketInJsonFormat(ticket), ticketJson);
		assertEquals(getTicketInJsonFormat(ticket), ticketViewAndResponseController.viewTicketComments(ticket));
		TicketComment newTicketComment = new TicketComment();
		newTicketComment.setComment("test");
		newTicketComment.setDateTime(new Timestamp(0));
		newTicketComment.setUserId(testUser.getUserId());
		newTicketComment.setTicketId(ticket.getTicketId());
		ticket.setNewTicketComment(newTicketComment);
		ticketJson = ticketViewAndResponseController.addComment(ticket);
		assertEquals(ticketJson, ticketViewAndResponseController.viewTicketComments(ticket));
		assertTrue(ticketDAO.deleteTicket(ticket));
		assertTrue(userDAO.deleteAccount(testUser));
	}

	@Test
	public void testWithBadUserId() {

		testTicketComment.setComment(testComment);
		testTicketComment.setUserId(0);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		ticket.setCategoryId(1);
		ticket.setComments(comments);
		assertEquals(SubmitTicketError.getDescriptionByCode(4), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testGetCategories() {

		assertNotNull("error", submitTicketController.openSubmitTicketPage());
	}
}
