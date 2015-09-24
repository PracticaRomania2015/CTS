package com.cts.communication;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.utils.ConfigReader;

@JsonSerialize
public class CategoryResponse implements ResponseMessage {
	
	private static Logger logger = Logger.getLogger(CategoryResponse.class.getName());

	private String type;
	private String description;
	
	private String categoryEmptyId;
	private String categoryEmptyName;
	private String unknownError;

	public CategoryResponse() {

		initAll();
	}

	private void initAll() {
		
		categoryEmptyId = ConfigReader.getInstance().getValueForKey("categoryEmptyId");
		categoryEmptyName = ConfigReader.getInstance().getValueForKey("categoryEmptyName");
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
			case CATEGORYEMPTYID: {
				type = "error";
				description = categoryEmptyId;
				break;
			}
			case CATEGORYEMPTYNAME: {
				type = "error";
				description = categoryEmptyName;
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
