package com.cts.errors;

public class SubmitTicketError {

	private String description;
	private static final String emptyTicketSubjectFieldError = "Ticket subject field cannot be empty!";
	private static final String ticketCategoryNotSelectedError = "You must select a category for the ticket!";
	private static final String emptyTicketDescriptionFieldError = "Ticket description field cannot be empty!";
	private static final String databaseError = "Database error when trying to create a new ticket!";
	private static final String unknownError = "Unknown error! Please refresh the page and try again!";

	public SubmitTicketError(int errorCode) {

		switch (errorCode) {

		case 1: {
			description = emptyTicketSubjectFieldError;
			break;
		}
		case 2: {
			description = ticketCategoryNotSelectedError;
			break;
		}
		case 3: {
			description = emptyTicketDescriptionFieldError;
			break;
		}
		case 4: {
			description = databaseError;
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
			return emptyTicketSubjectFieldError;
		case 2:
			return ticketCategoryNotSelectedError;
		case 3:
			return emptyTicketDescriptionFieldError;
		case 4:
			return databaseError;
		default:
			return unknownError;
		}
	}
}
