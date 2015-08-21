package com.cts.errors;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cts.errors.SubmitTicketError;

public class SubmitTicketErrorTest {

	private SubmitTicketError error;

	@Test
	public void testWhenGetEmptyTicketSubjectFieldError() {
		error = new SubmitTicketError(1);
		assertEquals(SubmitTicketError.getDescriptionByCode(1), error.getDescription());
	}

	@Test
	public void testWhenGetTicketCategoryNotSelectedError() {
		error = new SubmitTicketError(2);
		assertEquals(SubmitTicketError.getDescriptionByCode(2), error.getDescription());
	}

	@Test
	public void testWhenGetTicketSubcategoryNotSelectedError() {
		error = new SubmitTicketError(3);
		assertEquals(SubmitTicketError.getDescriptionByCode(3), error.getDescription());
	}

	@Test
	public void testWhenGetEmptyTicketDescriptionFieldError() {
		error = new SubmitTicketError(4);
		assertEquals(SubmitTicketError.getDescriptionByCode(4), error.getDescription());
	}
	
	@Test
	public void testWhenGetDbError() {
		error = new SubmitTicketError(5);
		assertEquals(SubmitTicketError.getDescriptionByCode(5), error.getDescription());
	}

	@Test
	public void testWhenGetUnknownError() {
		error = new SubmitTicketError(-1);
		assertEquals(SubmitTicketError.getDescriptionByCode(-1), error.getDescription());
	}
}
