package com.cts.communication;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.cts.utils.ConfigReader;

public class SubmitTicketError implements Error {

	private String emptyTicketSubjectFieldError;
	private String ticketCategoryNotSelectedError;
	private String emptyTicketDescriptionFieldError;
	private String databaseError;
	private String unknownError;
	protected ConfigReader configReader;
	protected String description;
	private ObjectMapper objectMapper = new ObjectMapper();

	public SubmitTicketError() {
		initAll();
	}

	public String getDescription() {

		return description;
	}

	private void initAll() {
		configReader = new ConfigReader();

		emptyTicketSubjectFieldError = configReader.getValueForKey("emptyTicketSubjectFieldError");
		ticketCategoryNotSelectedError = configReader.getValueForKey("ticketCategoryNotSelectedError");
		emptyTicketDescriptionFieldError = configReader.getValueForKey("emptyTicketDescriptionFieldError");
		databaseError = configReader.getValueForKey("databaseError");
		unknownError = configReader.getValueForKey("unknownError");
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

	private void initDescription(int errorCode) {

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
}
