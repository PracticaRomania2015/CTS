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

import com.cts.communication.RegisterResponse;
import com.cts.communication.ResponseValues;
import com.cts.communication.TicketResponse;
import com.cts.dao.TicketDAO;
import com.cts.dao.TicketDAOInterface;
import com.cts.dao.UserDAO;
import com.cts.dao.UserDAOInterface;
import com.cts.entities.Category;
import com.cts.entities.Priority;
import com.cts.entities.State;
import com.cts.entities.Ticket;
import com.cts.entities.TicketComment;
import com.cts.entities.User;
import com.cts.entities.ViewTicketsRequest;

public class SubmitTicketControllerTest {

	private static Ticket ticket;
	private static SubmitTicketController submitTicketController;
	private static TicketViewAndResponseController ticketViewAndResponseController;
	private static RegisterController registerController;
	private static LoginController loginController;
	private static ViewTicketsController viewTicketsController;
	private static TicketDAOInterface ticketDAO;
	private static UserDAOInterface userDAO;

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
		viewTicketsController = new ViewTicketsController();
		ticket = new Ticket();
		comments = new ArrayList<TicketComment>();
		testTicketComment = new TicketComment();
		testTicketComment.setDateTime(new Timestamp(0));
		testTicketComment.setFilePath(testFilePath);
		User user = new User();
		user.setUserId(0);
		testTicketComment.setUser(user);
	}

	@Test
	public void testWithNullSubject() {

		testTicketComment.setComment(testComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(nullSubject);
		Category category = new Category();
		category.setCategoryId(1);
		ticket.setCategory(category);
		ticket.setComments(comments);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYSUBJECTFIELD), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithEmptySubject() {

		testTicketComment.setComment(testComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(emptySubject);
		Category category = new Category();
		category.setCategoryId(1);
		ticket.setCategory(category);
		ticket.setComments(comments);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYSUBJECTFIELD), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithBadCategory() {

		testTicketComment.setComment(testComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		Category category = new Category();
		category.setCategoryId(0);
		ticket.setCategory(category);
		ticket.setComments(comments);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.TICKETNOCATEGORYSELECTED), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithNullComment() {

		testTicketComment.setComment(nullComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		Category category = new Category();
		category.setCategoryId(1);
		ticket.setCategory(category);
		ticket.setComments(comments);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYDESCRIPTIONFIELD), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithEmptyComment() {

		testTicketComment.setComment(emptyComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		Category category = new Category();
		category.setCategoryId(1);
		ticket.setCategory(category);
		ticket.setComments(comments);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.TICKETEMPTYDESCRIPTIONFIELD), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithValidInformation() {

		User testUser = new User();
		testUser.setFirstName("test");
		testUser.setEmail("testtesttest@gmail.com");
		testUser.setLastName("test");
		testUser.setTitle("test");
		testUser.setPassword("test");
		assertEquals(new RegisterResponse().getMessageJson(ResponseValues.REGISTERSUCCESS), registerController.register(testUser));
		testUser.setPassword("test");
		assertNotNull("error", loginController.login(testUser));
		testUser.setEmail("");
		testUser.setPassword("");
		testUser.setTitle("");
		testTicketComment.setUser(testUser);
		testTicketComment.setComment(testComment);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		Category category = new Category();
		category.setCategoryId(1);
		category.setCategoryName("HR");
		ticket.setCategory(category);
		ticket.setComments(comments);
		State state = new State();
		state.setStateId(1);
		state.setStateName("Active");
		ticket.setState(state);
		Priority priority = new Priority();
		priority.setPriorityId(2);
		priority.setPriorityName("Normal");
		ticket.setPriority(priority);
		User assignedUser = new User();
		assignedUser.setFirstName(null);
		assignedUser.setLastName(null);
		ticket.setAssignedToUser(assignedUser);
		String ticketJson = submitTicketController.submitTicket(ticket);
		assertEquals(getTicketInJsonFormat(ticket), ticketJson);
		assertEquals(getTicketInJsonFormat(ticket), ticketViewAndResponseController.viewTicket(ticket));
		TicketComment newTicketComment = new TicketComment();
		newTicketComment.setComment("test");
		newTicketComment.setDateTime(new Timestamp(0));
		newTicketComment.setUser(testUser);
		newTicketComment.setTicketId(ticket.getTicketId());
		ticket.getComments().add(newTicketComment);
		ticketJson = ticketViewAndResponseController.addComment(ticket);
		assertEquals(ticketJson, ticketViewAndResponseController.viewTicket(ticket));
		ViewTicketsRequest viewTicketsRequest = new ViewTicketsRequest();
		viewTicketsRequest.setUser(testUser);
		viewTicketsRequest.setTypeOfRequest(0);
		viewTicketsRequest.setRequestedPageNumber(1);
		viewTicketsRequest.setTicketsPerPage(1);
		assertNotNull("{\"totalNumberOfPages\":1,\"tickets\":[]}",
				viewTicketsController.viewTickets(viewTicketsRequest));
		ticket.setAssignedToUser(testUser);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.SUCCESS), ticketViewAndResponseController.assignTicket(ticket));
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.SUCCESS), ticketViewAndResponseController.closeTicket(ticket));
		assertTrue(ticketDAO.deleteTicket(ticket));
		assertTrue(userDAO.deleteAccount(testUser));
	}

	@Test
	public void testWithBadUserId() {

		testTicketComment.setComment(testComment);
		User user = new User();
		user.setUserId(0);
		testTicketComment.setUser(user);
		comments.clear();
		comments.add(testTicketComment);
		ticket.setSubject(testSubject);
		Category category = new Category();
		category.setCategoryId(1);
		ticket.setCategory(category);
		ticket.setComments(comments);
		assertEquals(new TicketResponse().getMessageJson(ResponseValues.DBERROR), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testGetCategories() {

		assertNotNull(new TicketResponse().getMessageJson(ResponseValues.UNKNOWN), submitTicketController.getCategories());
	}

	@Test
	public void testGetSubcategories() {

		Category category = new Category();
		category.setCategoryId(1);
		assertNotNull(new TicketResponse().getMessageJson(ResponseValues.UNKNOWN), submitTicketController.getSubcategories(category));
		category.setCategoryId(2);
		assertNotNull(new TicketResponse().getMessageJson(ResponseValues.UNKNOWN), submitTicketController.getSubcategories(category));
	}
}
