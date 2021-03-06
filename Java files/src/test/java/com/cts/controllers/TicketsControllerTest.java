package com.cts.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cts.communication.ResponseMessage;
import com.cts.communication.ResponseValues;
import com.cts.dao.CategoryDAO;
import com.cts.dao.PriorityDAO;
import com.cts.dao.TicketDAO;
import com.cts.dao.UserDAO;
import com.cts.entities.Category;
import com.cts.entities.Priority;
import com.cts.entities.Ticket;
import com.cts.entities.TicketComment;
import com.cts.entities.User;
import com.cts.entities.ViewTicketsRequest;

public class TicketsControllerTest {

	private static LoginController loginController;
	private static RegisterController registerController;
	private static RootManagementController rootManagementController;
	private static TicketsController ticketsController;
	private static CategoryDAO categoryDAO;
	private static UserDAO userDAO;
	private static TicketDAO ticketDAO;
	private static PriorityDAO priorityDAO;
	private static Category validCategory, testCategory;
	private static User validUser, testUser;
	private static Ticket validTicket, testTicket;
	private static TicketComment validTicketComment, testTicketComment;
	private static Priority validPriority, testPriority;
	private static ArrayList<TicketComment> validComments, testComments;
	private static Date date;
	
	private static final String empty = "";
	
	private static final String validTitle = "Mr.";
	private static final String validFirstName = "testFirstName";
	private static final String validLastName = "testLastName";
	private static final String validEmail = "test@gmail.com";
	private static final String validPassword = "testPassword";
	private static final String validTicketSubject = "testTicketSubject";
	private static final String validComment = "testTicketComment";
	private static final String validNewComment = "testNewTicketComment";
	private static final String validCategoryName = "testCategory";
	
	private static final int invalidTicketId = 0;
	private static final int invalidUserId = -1;
	private static final int reservedUserId = 0;
	private static final int invalidCategoryId = 0;

	@BeforeClass
	public static void beforeClass() {

		loginController = new LoginController();
		registerController = new RegisterController();
		rootManagementController = new RootManagementController();
		ticketsController = new TicketsController();
		categoryDAO =  new CategoryDAO();
		userDAO = new UserDAO();
		ticketDAO = new TicketDAO();
		priorityDAO = new PriorityDAO();
		
		validCategory = new Category();
		validUser = new User();
		validTicket = new Ticket();
		validTicketComment = new TicketComment();
		validComments = new ArrayList<TicketComment>();
		validPriority = new Priority();
	
		date = new Date();
				
		//valid account
		validUser.setTitle(validTitle);
		validUser.setFirstName(validFirstName);
		validUser.setLastName(validLastName);
		validUser.setEmail(validEmail);
		validUser.setPassword(validPassword);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.REGISTERSUCCESS), registerController.register(validUser));
		validUser.setPassword(validPassword);
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS), new ResponseMessage().getMessageJSON(ResponseValues.LOGININVALIDCREDENTIALS).equals(loginController.login(validUser)));
		
		//valid category
		validCategory.setCategoryName(validCategoryName);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.SUCCESS), rootManagementController.addCategory(validCategory));
		ArrayList<Category> categories = categoryDAO.getCategories();
		for(Category category : categories){
			if (category.getCategoryName().equals(validCategoryName)){
				validCategory.setCategoryId(category.getCategoryId());
			}
		}
		
		//valid ticket comment
		validTicketComment.setUser(validUser);
		validTicketComment.setDateTime(new Timestamp(date.getTime()));
		validTicketComment.setComment(validComment);
		
		//valid comments
		validComments.add(validTicketComment);
		
		//valid priority
		ArrayList<Priority> priorities = priorityDAO.getPriorities();
		if (priorities.size() != 0){
			validPriority.setPriorityId(priorities.get(0).getPriorityId());
			validPriority.setPriorityName(priorities.get(0).getPriorityName());
		}
		
		//valid ticket
		validTicket.setSubject(validTicketSubject);
		validTicket.setCategory(validCategory);
		validTicket.setComments(validComments);
		validTicket.setPriority(validPriority);
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), new ResponseMessage().getMessageJSON(ResponseValues.DBERROR).equals(ticketsController.submitTicket(validTicket)));
	}
	
	@AfterClass
	public static void afterClass(){
		
		//delete valid account
		assertTrue(userDAO.deleteAccount(validUser));
		//delete valid category
		//TODO: actual delete instead of disable
		assertTrue(categoryDAO.deleteCategory(validCategory));
		//delete valid ticket
		assertTrue(ticketDAO.deleteTicket(validTicket));
	}
	
	@Test
	public void testGetTicketWithInvalidData() {

		Ticket ticket = new Ticket();
		ticket.setTicketId(invalidTicketId);
		assertTrue(new ResponseMessage().getMessageJSON(ResponseValues.UNKNOWN), new ResponseMessage().getMessageJSON(ResponseValues.UNKNOWN).equals(ticketsController.getTicket(ticket)));
	}
	
	@Test
	public void testGetTicketWithValidData() {
		
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.UNKNOWN), new ResponseMessage().getMessageJSON(ResponseValues.UNKNOWN).equals(ticketsController.getTicket(validTicket)));
	}
	
	@Test
	public void testGetTicketsWithValidData() {
		
		ViewTicketsRequest viewTicketRequest = new ViewTicketsRequest();
		viewTicketRequest.setUser(testUser);
		viewTicketRequest.setTypeOfRequest(0);
		viewTicketRequest.setRequestedPageNumber(1);
		viewTicketRequest.setTicketsPerPage(10);
		StringBuilder totalNumberOfPages = new StringBuilder("1");
		ArrayList<Object> output = new ArrayList<Object>();
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		output.add(totalNumberOfPages);
		output.add(tickets);
		assertEquals(new ResponseMessage(output).getMessageJSON(ResponseValues.SUCCESS), ticketsController.getTickets(viewTicketRequest));
	}
	
	@Test
	public void testSubmitTicketWithNullSubject() {

		testTicket = new Ticket();
		testTicket.setSubject(null);
		testTicket.setCategory(validCategory);
		testTicket.setComments(validComments);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYSUBJECT), ticketsController.submitTicket(testTicket));
	}
	
	@Test
	public void testSubmitTicketWithEmptySubject() {
		
		testTicket = new Ticket();
		testTicket.setSubject(empty);
		testTicket.setCategory(validCategory);
		testTicket.setComments(validComments);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYSUBJECT), ticketsController.submitTicket(testTicket));
	}
	
	@Test
	public void testSubmitTicketWithNullCategory() {
		
		testTicket = new Ticket();
		testTicket.setSubject(validTicketSubject);
		testTicket.setCategory(null);
		testTicket.setComments(validComments);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYCATEGORY), ticketsController.submitTicket(testTicket));
	}
	
	@Test
	public void testSubmitTicketWithInvalidCategory() {
		
		//testCategory
		testCategory = new Category();
		testCategory.setCategoryName(validCategoryName);
		testCategory.setCategoryId(invalidCategoryId);
		//ticket
		testTicket = new Ticket();
		testTicket.setSubject(validTicketSubject);
		testTicket.setCategory(testCategory);
		testTicket.setComments(validComments);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYCATEGORY), ticketsController.submitTicket(testTicket));
	}
	
	@Test
	public void testSubmitTicketWithNullComment() {
		
		//ticketComment
		testTicketComment = new TicketComment();
		testTicketComment.setComment(null);
		testTicketComment.setUser(validUser);
		testTicketComment.setDateTime(new Timestamp(date.getTime()));
		testComments = new ArrayList<TicketComment>();
		testComments.add(testTicketComment);
		//ticket
		testTicket = new Ticket();
		testTicket.setSubject(validTicketSubject);
		testTicket.setCategory(validCategory);
		testTicket.setComments(testComments);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYDESCRIPTION), ticketsController.submitTicket(testTicket));
	}
	
	@Test
	public void testSubmitTicketWithEmptyComment() {
		
		//ticketComment
		testTicketComment = new TicketComment();
		testTicketComment.setComment(empty);
		testTicketComment.setUser(validUser);
		testTicketComment.setDateTime(new Timestamp(date.getTime()));
		testComments = new ArrayList<TicketComment>();
		testComments.add(testTicketComment);
		//ticket
		testTicket = new Ticket();
		testTicket.setSubject(validTicketSubject);
		testTicket.setCategory(validCategory);
		testTicket.setComments(testComments);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYDESCRIPTION), ticketsController.submitTicket(testTicket));
	}
	
	@Test
	public void testSubmitTicketWithInvalidUser() {
		
		//user
		testUser = new User();
		testUser.setUserId(invalidUserId);
		testUser.setTitle(validTitle);
		testUser.setFirstName(validFirstName);
		testUser.setLastName(validLastName);
		testUser.setEmail(validEmail);
		testUser.setPassword(validPassword);
		//ticketComment
		testTicketComment = new TicketComment();
		testTicketComment.setComment(validComment);
		testTicketComment.setUser(testUser);
		testTicketComment.setDateTime(new Timestamp(date.getTime()));
		testComments = new ArrayList<TicketComment>();
		testComments.add(testTicketComment);
		//ticket
		testTicket = new Ticket();
		testTicket.setSubject(validTicketSubject);
		testTicket.setCategory(validCategory);
		testTicket.setComments(testComments);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), ticketsController.submitTicket(testTicket));
	}
	
	@Test
	public void testSubmitTicketWithValidData() {
		
		testTicket = new Ticket();
		testTicket.setSubject(validTicketSubject);
		testTicket.setCategory(validCategory);
		testTicket.setComments(validComments);
		testTicket.setPriority(validPriority);
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), new ResponseMessage().getMessageJSON(ResponseValues.DBERROR).equals(ticketsController.submitTicket(testTicket)));
		assertTrue(ticketDAO.deleteTicket(testTicket));
	}
	
	@Test
	public void testAddCommentToTicketWithNullComment() {
		
		//ticketComment
		testTicketComment = new TicketComment();
		testTicketComment.setComment(null);
		testTicketComment.setUser(validUser);
		testTicketComment.setDateTime(new Timestamp(date.getTime()));
		testComments = new ArrayList<TicketComment>();
		testComments.add(testTicketComment);
		//ticket
		testTicket = new Ticket();
		testTicket.setSubject(null);
		testTicket.setCategory(null);
		testTicket.setComments(testComments);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYCOMMENT), ticketsController.addCommentToTicket(testTicket));
	}
	
	@Test
	public void testAddCommentToTicketWithEmptyComment() {
		
		//ticketComment
		testTicketComment = new TicketComment();
		testTicketComment.setComment(empty);
		testTicketComment.setUser(validUser);
		testTicketComment.setDateTime(new Timestamp(date.getTime()));
		testComments = new ArrayList<TicketComment>();
		testComments.add(testTicketComment);
		//ticket
		testTicket = new Ticket();
		testTicket.setSubject(null);
		testTicket.setCategory(null);
		testTicket.setComments(testComments);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.TICKETEMPTYCOMMENT), ticketsController.addCommentToTicket(testTicket));
	}
	
	@Test
	public void testAddCommentToTicketWithValidData() {
		
		//ticketComment
		testTicketComment = new TicketComment();
		testTicketComment.setComment(validNewComment);
		testTicketComment.setUser(validUser);
		testTicketComment.setDateTime(new Timestamp(date.getTime()));
		testComments = new ArrayList<TicketComment>();
		testComments.add(testTicketComment);
		//ticket
		validTicket.setComments(testComments);
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), new ResponseMessage().getMessageJSON(ResponseValues.DBERROR).equals(ticketsController.addCommentToTicket(validTicket)));
	}
	
	@Test
	public void testAssignAdminToTicketWithNullTicket() {
		
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), ticketsController.assignAdminToTicket(null));
	}
	
	@Test
	public void testAssignAdminToTicketWithInvalidTicket() {
		
		testTicket = new Ticket();
		testTicket.setTicketId(invalidTicketId);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), ticketsController.assignAdminToTicket(testTicket));
	}
	
	@Test
	public void testAssignAdminToTicketWithInvalidUserId() {
		
		//user
		testUser = new User();
		testUser.setUserId(invalidUserId);
		testUser.setTitle(validTitle);
		testUser.setFirstName(validFirstName);
		testUser.setLastName(validLastName);
		testUser.setEmail(validEmail);
		testUser.setPassword(validPassword);
		//ticket
		testTicket = new Ticket();
		testTicket.setAssignedToUser(testUser);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), ticketsController.assignAdminToTicket(testTicket));
	}
	
	@Test
	public void testAssignAdminToTicketWithValidData() {
		
		validTicket.setAssignedToUser(validUser);
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), new ResponseMessage().getMessageJSON(ResponseValues.DBERROR).equals(ticketsController.assignAdminToTicket(validTicket)));
	}
	
	@Test
	public void testAssignAdminToTicketWithValidDataUnassign() {
		
		//user
		testUser = new User();
		testUser.setUserId(reservedUserId);
		testUser.setTitle(validTitle);
		testUser.setFirstName(validFirstName);
		testUser.setLastName(validLastName);
		testUser.setEmail(validEmail);
		testUser.setPassword(validPassword);
		//ticket
		testTicket = new Ticket();
		testTicket.setAssignedToUser(testUser);
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), new ResponseMessage().getMessageJSON(ResponseValues.DBERROR).equals(ticketsController.assignAdminToTicket(testTicket)));
	}
	
	@Test
	public void testCloseTicketWithNullTicket() {
		
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), ticketsController.closeTicket(null));
	}
	
	@Test
	public void testCloseTicketWithInvalidTicket() {
		
		testTicket = new Ticket();
		testTicket.setTicketId(invalidTicketId);
		assertEquals(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), ticketsController.closeTicket(testTicket));
	}
	
	@Test
	public void testCloseTicketWithValidData() {
		
		assertFalse(new ResponseMessage().getMessageJSON(ResponseValues.DBERROR), new ResponseMessage().getMessageJSON(ResponseValues.DBERROR).equals(ticketsController.closeTicket(testTicket)));
	}
}