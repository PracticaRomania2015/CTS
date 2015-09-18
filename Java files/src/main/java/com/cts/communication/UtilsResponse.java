package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class UtilsResponse implements ResponseMessage {

	private String responseType;
	private String unknownError;
	private String description;

	public UtilsResponse() {

		initAll();
	}

	private void initAll() {
		
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
			case ERROR: {
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
