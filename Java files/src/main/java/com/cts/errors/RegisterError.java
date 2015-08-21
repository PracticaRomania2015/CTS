package com.cts.errors;

public class RegisterError {

	private String description;
	private static final String emptyEmailFieldError = "Email field cannot be empty!";
	private static final String emptyPasswordFieldError = "Password field cannot be empty!";
	private static final String emptyFirstNameFieldError = "Firstname field cannot be empty!";
	private static final String emptyLastNameFieldError = "Lastname field cannot be empty!";
	private static final String invalidEmailError = "Invalid email!";
	private static final String emptyFields = "All fields are mandatory!";
	private static final String dbError = "Something went wrong with the database, please try again!";
	private static final String unknownError = "Unknown error! Please refresh the page and try again!";
	private static final String existingEmailError = "The email specified already exists!";
	private static final String success = "Success!";

	public RegisterError(int errorCode) {

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
			description = emptyFirstNameFieldError;
			break;
		}
		case 4: {
			description = emptyLastNameFieldError;
			break;
		}
		case 5: {
			description = invalidEmailError;
			break;
		}

		case 6: {
			description = emptyFields;
			break;
		}

		case 7: {
			description = dbError;
			break;
		}
		case 8: {
			description = success;
			break;
		}
		case 9: {
			description = existingEmailError;
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
			return emptyFirstNameFieldError;
		case 4:
			return emptyLastNameFieldError;
		case 5:
			return invalidEmailError;
		case 6:
			return emptyFields;
		case 7:
			return dbError;
		case 8:
			return success;
		case 9:
			return existingEmailError;
		default:
			return unknownError;
		}
	}
}