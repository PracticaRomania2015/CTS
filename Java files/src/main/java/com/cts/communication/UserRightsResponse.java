package com.cts.communication;
import java.io.IOException;
import com.cts.utils.ConfigReader;

public class UserRightsResponse implements ResponseMessage {

	private String responseType;
	private String unknownError;
	private String dbError;

	private String description;
	
	public UserRightsResponse() {

		initAll();
	}

	private void initAll() {
		unknownError = ConfigReader.getInstance().getValueForKey("unknownError");
		dbError = ConfigReader.getInstance().getValueForKey("dbError");
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
		
		String messageJson = "";

		try {
			messageJson = objectMapper.writeValueAsString(this);
		} catch (IOException e) {
		}
		return messageJson;
	}

}
