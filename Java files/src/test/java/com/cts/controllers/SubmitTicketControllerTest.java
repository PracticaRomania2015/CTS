package com.cts.controllers;

import static org.junit.Assert.*;
import java.io.IOException;
import java.sql.Timestamp;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import com.cts.dao.TicketDAOInterface;
import com.cts.dao.TicketDAO;
import com.cts.controllers.SubmitTicketController;
import com.cts.entities.Ticket;
import com.cts.errors.SubmitTicketError;

public class SubmitTicketControllerTest {

	private static Ticket ticket;
	private static SubmitTicketController submitTicketController;
	private static TicketDAOInterface ticketDAO;

	private static final String nullSubject = null;
	private static final String emptySubject = "";
	private static final String testSubject = "test";

	private static final String nullCategory = null;
	private static final String emptyCategory = "";
	private static final String testCategory = "test";

	private static final String nullDescription = null;
	private static final String emptyDescription = "";
	private static final String testDescription = "test";

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

		ticketDAO = new TicketDAO();
		submitTicketController = new SubmitTicketController();
		ticket = new Ticket();
	}

	@Test
	public void testWithNullSubject() {

		ticket.setSubject(nullSubject);
		ticket.setCategory(testCategory);
		ticket.setDescription(testDescription);
		ticket.setUserId(0);
		ticket.setDateTime(new Timestamp(0));
		ticket.setFilePath(testFilePath);
		assertEquals(SubmitTicketError.getDescriptionByCode(1), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithEmptySubject() {

		ticket.setSubject(emptySubject);
		ticket.setCategory(testCategory);
		ticket.setDescription(testDescription);
		ticket.setUserId(0);
		ticket.setDateTime(new Timestamp(0));
		ticket.setFilePath(testFilePath);
		assertEquals(SubmitTicketError.getDescriptionByCode(1), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithNullCategory() {

		ticket.setSubject(testSubject);
		ticket.setCategory(nullCategory);
		ticket.setDescription(testDescription);
		ticket.setUserId(0);
		ticket.setDateTime(new Timestamp(0));
		ticket.setFilePath(testFilePath);
		assertEquals(SubmitTicketError.getDescriptionByCode(2), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithEmptyCategory() {

		ticket.setSubject(testSubject);
		ticket.setCategory(emptyCategory);
		ticket.setDescription(testDescription);
		ticket.setUserId(0);
		ticket.setDateTime(new Timestamp(0));
		ticket.setFilePath(testFilePath);
		assertEquals(SubmitTicketError.getDescriptionByCode(2), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithNullDescription() {

		ticket.setSubject(testSubject);
		ticket.setCategory(testCategory);
		ticket.setDescription(nullDescription);
		ticket.setUserId(0);
		ticket.setDateTime(new Timestamp(0));
		ticket.setFilePath(testFilePath);
		assertEquals(SubmitTicketError.getDescriptionByCode(3), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithEmptyDescription() {

		ticket.setSubject(testSubject);
		ticket.setCategory(testCategory);
		ticket.setDescription(emptyDescription);
		ticket.setUserId(0);
		ticket.setDateTime(new Timestamp(0));
		ticket.setFilePath(testFilePath);
		assertEquals(SubmitTicketError.getDescriptionByCode(3), submitTicketController.submitTicket(ticket));
	}

	@Test
	public void testWithValidInformation() {

		ticket.setSubject(testSubject);
		ticket.setCategory("HR");
		ticket.setDescription(testDescription);
		ticket.setUserId(0);
		ticket.setDateTime(new Timestamp(0));
		ticket.setFilePath(testFilePath);
		assertEquals(submitTicketController.submitTicket(ticket), getTicketInJsonFormat(ticket));
		assertTrue(ticketDAO.deleteTicket(ticket));
	}

	@Test
	public void testGetCategories() {

		assertNotNull("error", submitTicketController.openSubmitTicketPage());
	}
}
