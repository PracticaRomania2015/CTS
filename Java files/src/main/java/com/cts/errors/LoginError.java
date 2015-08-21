package com.cts.errors;

public class LoginError {

	private String description;
	private static final String emptyEmailFieldError = "Email field cannot be empty!";
	private static final String emptyPasswordFieldError = "Password field cannot be empty!";
	private static final String invalidEmailOrPasswordError = "Invalid email or password!";
	private static final String unknownError = "Unknown error! Please refresh the page and try again!";

	public LoginError(int errorCode) {

		switch (errorCode) {

		case 1: {
			description = emptyEmailFieldError;
			break;
		}
		case 2: {
			description = emptyPasswordFieldError;
			break;
		}
		case 3: {
			description = invalidEmailOrPasswordError;
			break;
		}

		default: {
			description = unknownError;
			break;
		}
		}
	}

	public String getDescription() {

		return description;
	}

	public static String getDescriptionByCode(int errorCode) {

		switch (errorCode) {
		case 1:
			return emptyEmailFieldError;
		case 2:
			return emptyPasswordFieldError;
		case 3:
			return invalidEmailOrPasswordError;
		default:
			return unknownError;
		}
	}
}
