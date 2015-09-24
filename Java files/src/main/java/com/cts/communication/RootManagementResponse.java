package com.cts.communication;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.utils.ConfigReader;

@JsonSerialize
public class RootManagementResponse implements ResponseMessage {
	
	private static Logger logger = Logger.getLogger(RootManagementResponse.class.getName());

	private String type;
	private String description;
	
	private String rootManagementEmptySysAdmin;
	private String dbError;
	private String unknownError;
	
	
	public RootManagementResponse() {

		initAll();
	}

	private void initAll() {
		
		rootManagementEmptySysAdmin = ConfigReader.getInstance().getValueForKey("rootManagementEmptySysAdmin");
		dbError = ConfigReader.getInstance().getValueForKey("dbError");
		unknownError = ConfigReader.getInstance().getValueForKey("unknownError");
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
			case ROOTMANAGEMENTEMPTYSYSADMIN: {
				type = "error";
				description = rootManagementEmptySysAdmin;
				break;
			}
			case DBERROR: {
				type = "error";
				description = dbError;
				break;
			}
			default: {
				type = "error";
				description = unknownError;
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
