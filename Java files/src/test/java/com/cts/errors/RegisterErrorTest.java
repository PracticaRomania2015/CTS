package com.cts.errors;

import static org.junit.Assert.*;
import org.junit.Test;

import com.cts.errors.RegisterError;

public class RegisterErrorTest {

	private RegisterError error;

	@Test
	public void testWhenGetEmptyEmailFieldError() {
		error = new RegisterError(1);
		assertEquals(RegisterError.getDescriptionByCode(1), error.getDescription());
	}

	@Test
	public void testWhenGetEmptyPasswordFieldError() {
		error = new RegisterError(2);
		assertEquals(RegisterError.getDescriptionByCode(2), error.getDescription());
	}

	@Test
	public void testWhenGetEmptyFirstNameFieldError() {
		error = new RegisterError(3);
		assertEquals(RegisterError.getDescriptionByCode(3), error.getDescription());
	}

	@Test
	public void testWhenGetEmptyLastNameFieldError() {
		error = new RegisterError(4);
		assertEquals(RegisterError.getDescriptionByCode(4), error.getDescription());
	}

	@Test
	public void testWhenGetInvalidEmailError() {
		error = new RegisterError(5);
		assertEquals(RegisterError.getDescriptionByCode(5), error.getDescription());
	}
	
	@Test
	public void testWhenGetEmptyFieldsError() {
		error = new RegisterError(6);
		assertEquals(RegisterError.getDescriptionByCode(6), error.getDescription());
	}
	
	@Test
	public void testWhenGetDbError() {
		error = new RegisterError(7);
		assertEquals(RegisterError.getDescriptionByCode(7), error.getDescription());
	}

	@Test
	public void testWhenGetUnknownError() {
		error = new RegisterError(-1);
		assertEquals(RegisterError.getDescriptionByCode(-1), error.getDescription());
	}
	
	@Test
	public void testWhenGetSuccess() {
		error = new RegisterError(8);
		assertEquals(RegisterError.getDescriptionByCode(8), error.getDescription());
	}
	
	@Test
	public void testWhenGetExistingEmailError() {
		error = new RegisterError(9);
		assertEquals(RegisterError.getDescriptionByCode(9), error.getDescription());
	}
}
