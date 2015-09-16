package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class TicketResponse implements ResponseMessage {

	private String responseType;
	private String ticketEmptySubject;
	private String ticketEmptyCategory;
	private String ticketEmptyDescription;
	private String ticketEmptyComment;
	private String dbError;
	private String unknownError;
	private String description;

	public TicketResponse() {

		initAll();
	}

	private void initAll() {
		ticketEmptySubject = ConfigReader.getInstance().getValueForKey("ticketEmptySubject");
		ticketEmptyCategory = ConfigReader.getInstance().getValueForKey("ticketEmptyCategory");
		ticketEmptyDescription = ConfigReader.getInstance().getValueForKey("ticketEmptyDescription");
		ticketEmptyComment = ConfigReader.getInstance().getValueForKey("ticketEmptyComment");
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
			case TICKETEMPTYSUBJECT: {
				description = ticketEmptySubject;
				responseType = "error";
				break;
			}
			case TICKETEMPTYCATEGORY: {
				description = ticketEmptyCategory;
				responseType = "error";
				break;
			}
			case TICKETEMPTYDESCRIPTION: {
				description = ticketEmptyDescription;
				responseType = "error";
				break;
			}
			case TICKETEMPTYCOMMENT: {
				description = ticketEmptyComment;
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
