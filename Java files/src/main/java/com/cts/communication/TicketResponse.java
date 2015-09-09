package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class TicketResponse implements ResponseMessage {

	private String responseType;
	private String ticketEmptySubjectField;
	private String ticketNoCategorySelected;
	private String ticketEmptyDescriptionField;
	private String ticketEmptyCommentField;
	private String dbError;
	private String unknownError;
	private String description;

	public TicketResponse() {

		initAll();
	}

	private void initAll() {
		ticketEmptySubjectField = ConfigReader.getInstance().getValueForKey("ticketEmptySubjectField");
		ticketNoCategorySelected = ConfigReader.getInstance().getValueForKey("ticketNoCategorySelected");
		ticketEmptyDescriptionField = ConfigReader.getInstance().getValueForKey("ticketEmptyDescriptionField");
		ticketEmptyCommentField = ConfigReader.getInstance().getValueForKey("ticketEmptyCommentField");
		dbError = ConfigReader.getInstance().getValueForKey("dbError");
		unknownError = ConfigReader.getInstance().getValueForKey("unknownError");
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getResponseType() {
		return responseType;
	}

	@Override
	public void initDescription(ResponseValues responseValue) {

		switch (responseValue) {
			case SUCCESS: {
				responseType = "success";
				break;
			}
			case ERROR: {
				responseType = "error";
				break;
			}
			case TICKETEMPTYSUBJECTFIELD: {
				description = ticketEmptySubjectField;
				responseType = "error";
				break;
			}
			case TICKETNOCATEGORYSELECTED: {
				description = ticketNoCategorySelected;
				responseType = "error";
				break;
			}
			case TICKETEMPTYDESCRIPTIONFIELD: {
				description = ticketEmptyDescriptionField;
				responseType = "error";
				break;
			}
			case TICKETEMPTYCOMMENTFIELD: {
				description = ticketEmptyCommentField;
				responseType = "error";
				break;
			}
			case DBERROR: {
				description = dbError;
				responseType = "error";
				break;
			}
			default: {
				description = unknownError;
				responseType = "error";
				break;
			}
		}
	}

	@Override
	public String getMessageJson(ResponseValues responseValue) {
		initDescription(responseValue);
				
		String errorMessageJson = "";

		try {
			errorMessageJson = objectMapper.writeValueAsString(this);
			
		} catch (IOException e) {
		}
		return errorMessageJson;
	}
}
