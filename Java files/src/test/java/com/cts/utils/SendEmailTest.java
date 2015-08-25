package com.cts.utils;

import static org.junit.Assert.*;
import org.junit.Test;

public class SendEmailTest {

	@Test
	public void testWithBadEmail() {
		
		assertFalse(SendEmail.sendEmail("test", "subject", "message"));
	}
	
	@Test
	public void testWithValidEmail() {
		
		assertTrue(SendEmail.sendEmail("test@test.com", "subject", "message"));
	}
}
