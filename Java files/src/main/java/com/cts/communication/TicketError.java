package com.cts.communication;

import com.cts.utils.ConfigReader;

public class TicketError extends Error {

	private String emptyTicketSubjectFieldError;
	private String ticketCategoryNotSelectedError;
	private String emptyTicketDescriptionFieldError;
	private String emptyTicketCommentFieldError;
	private String databaseError;
	private String unknownError;

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
	protected void initDescription(int errorCode) {

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
}
