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

	private ConfigReader configReader;

	public TicketResponse() {

		initAll();
	}

	private void initAll() {
		configReader = new ConfigReader();
		
		ticketEmptySubjectField = configReader.getValueForKey("ticketEmptySubjectField");
		ticketNoCategorySelected = configReader.getValueForKey("ticketNoCategorySelected");
		ticketEmptyDescriptionField = configReader.getValueForKey("ticketEmptyDescriptionField");
		ticketEmptyCommentField = configReader.getValueForKey("ticketEmptyCommentField");
		dbError = configReader.getValueForKey("dbError");
		unknownError = configReader.getValueForKey("unknownError");
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
			}
			case ERROR: {
				responseType = "error";
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
