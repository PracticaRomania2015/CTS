package com.cts.errors;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cts.errors.LoginError;

public class LoginErrorTest {

	private LoginError error;

	@Test
	public void testWhenGetEmptyEmailFieldError() {
		error = new LoginError(1);
		assertEquals(LoginError.getDescriptionByCode(1), error.getDescription());
	}

	@Test
	public void testWhenGetEmptyPasswordFieldError() {
		error = new LoginError(2);
		assertEquals(LoginError.getDescriptionByCode(2), error.getDescription());
	}

	@Test
	public void testWhenGetInvalidEmailOrPasswordError() {
		error = new LoginError(3);
		assertEquals(LoginError.getDescriptionByCode(3), error.getDescription());
	}

	@Test
	public void testWhenGetUnknownError() {
		error = new LoginError(-1);
		assertEquals(LoginError.getDescriptionByCode(-1), error.getDescription());
	}
}
