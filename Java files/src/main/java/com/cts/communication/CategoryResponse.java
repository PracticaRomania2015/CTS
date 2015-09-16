package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class CategoryResponse implements ResponseMessage {

	private String responseType;
	private String emptyCategoryName;
	private String unknownError;

	private String description;
	
	public CategoryResponse() {

		initAll();
	}

	private void initAll() {
		emptyCategoryName = ConfigReader.getInstance().getValueForKey("emptyCategoryName");
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
			case EMPTYCATEGORYNAME: {
				description = emptyCategoryName;
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
		
		String messageJson = "";

		try {
			messageJson = objectMapper.writeValueAsString(this);
		} catch (IOException e) {
		}
		return messageJson;
	}

}
