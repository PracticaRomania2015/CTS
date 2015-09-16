package com.cts.communication;

import java.io.IOException;

public class CategoryResponse implements ResponseMessage {

	private String responseType;
	private String unknownError;

	private String description;
	
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
		
		String messageJson = "";

		try {
			messageJson = objectMapper.writeValueAsString(this);
		} catch (IOException e) {
		}
		return messageJson;
	}

}
