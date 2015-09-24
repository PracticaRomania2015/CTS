package com.cts.communication;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.utils.ConfigReader;

@JsonSerialize
public class UtilsResponse implements ResponseMessage {
	
	private static Logger logger = Logger.getLogger(UtilsResponse.class.getName());

	private Object data;
	private String type;
	private String description;
	
	private String dbError;
	private String unknownError;
	
	
	public UtilsResponse() {

		initAll();
	}
	
	public UtilsResponse(Object data){
		
		this.data = data;
	}
	
	private void initAll() {
		
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
