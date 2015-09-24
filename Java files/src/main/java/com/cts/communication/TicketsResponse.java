package com.cts.communication;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.utils.ConfigReader;

@JsonSerialize
public class TicketsResponse implements ResponseMessage {
	
	private static Logger logger = Logger.getLogger(TicketsResponse.class.getName());

	private Object data;
	private String type;
	private String description;
	
	private String ticketEmptySubject;
	private String ticketEmptyCategory;
	private String ticketEmptyDescription;
	private String ticketEmptyComment;
	private String dbError;
	private String unknownError;

	public TicketsResponse() {

		initAll();
	}
	
	public TicketsResponse(Object data){
		
		this.data = data;
	}

	private void initAll() {
		
		ticketEmptySubject = ConfigReader.getInstance().getValueForKey("ticketEmptySubject");
		ticketEmptyCategory = ConfigReader.getInstance().getValueForKey("ticketEmptyCategory");
		ticketEmptyDescription = ConfigReader.getInstance().getValueForKey("ticketEmptyDescription");
		ticketEmptyComment = ConfigReader.getInstance().getValueForKey("ticketEmptyComment");
		dbError = ConfigReader.getInstance().getValueForKey("dbError");
		unknownError = ConfigReader.getInstance().getValueForKey("unknownError");
	}
	
	public Object getData() {
		
		return data;
	}
	
	public String getType() {
		
		return type;
	}

	public String getDescription() {
		
		return description;
	}
	
	@Override
	public void initDescription(ResponseValues responseValue) {

		switch (responseValue) {
			case SUCCESS: {
				type = "success";
				break;
			}
			case TICKETEMPTYSUBJECT: {
				description = ticketEmptySubject;
				type = "error";
				break;
			}
			case TICKETEMPTYCATEGORY: {
				description = ticketEmptyCategory;
				type = "error";
				break;
			}
			case TICKETEMPTYDESCRIPTION: {
				description = ticketEmptyDescription;
				type = "error";
				break;
			}
			case TICKETEMPTYCOMMENT: {
				description = ticketEmptyComment;
				type = "error";
				break;
			}
			case DBERROR: {
				description = dbError;
				type = "error";
				break;
			}
			default: {
				description = unknownError;
				type = "error";
				break;
			}
		}
	}

	@Override
	public String getMessageJSON(ResponseValues responseValue) {
		
		initDescription(responseValue);
		try {
			
			String response = objectMapper.writeValueAsString(this);
			logger.debug("JSON response created successfully!");
			return response;
		} catch (IOException e) {
			
			logger.error("Could not create JSON response!");
			return "Could not create JSON";
		}
	}
}
