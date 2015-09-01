package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class TicketError implements Error {

	private String emptyTicketSubjectFieldError;
	private String ticketCategoryNotSelectedError;
	private String emptyTicketDescriptionFieldError;
	private String emptyTicketCommentFieldError;
	private String databaseError;
	private String unknownError;
	private String description;

	private ConfigReader configReader;

	public TicketError() {

		initAll();
	}

	private void initAll() {
		configReader = new ConfigReader();

		emptyTicketSubjectFieldError = configReader.getValueForKey("emptyTicketSubjectFieldError");
		ticketCategoryNotSelectedError = configReader.getValueForKey("ticketCategoryNotSelectedError");
		emptyTicketDescriptionFieldError = configReader.getValueForKey("emptyTicketDescriptionFieldError");
		emptyTicketCommentFieldError = configReader.getValueForKey("emptyTicketCommentFieldError");
		databaseError = configReader.getValueForKey("databaseError");
		unknownError = configReader.getValueForKey("unknownError");
	}

	@Override
	public void initDescription(int errorCode) {

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
		case 5: {

			description = emptyTicketCommentFieldError;
			break;
		}
		default: {

			description = unknownError;
			break;
		}
		}
	}

	@Override
	public String getErrorJson(int errorCode) {
		initDescription(errorCode);

		String errorMessageJson = "";

		try {
			errorMessageJson = objectMapper.writeValueAsString(this);
		} catch (IOException e) {
		}
		return errorMessageJson;
	}

	public String getDescription() {
		return description;
	}
}
