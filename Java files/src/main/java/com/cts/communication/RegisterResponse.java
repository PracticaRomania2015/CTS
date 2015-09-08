package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class RegisterResponse implements ResponseMessage {

	private String responseType;
	private String registerSuccess;
	private String registerEmptyTitle;
	private String registerEmptyFirstNameField;
	private String registerEmptyLastNameField;
	private String invalidEmailFormat;
	private String registerExistingEmail;
	private String dbError;
	private String unknownError;
	private String description;

	public RegisterResponse() {
		initAll();
	}

	private void initAll() {
		registerSuccess = ConfigReader.getInstance().getValueForKey("registerSuccess");
		invalidEmailFormat = ConfigReader.getInstance().getValueForKey("invalidEmailFormat");
		registerEmptyTitle = ConfigReader.getInstance().getValueForKey("registerEmptyTitle");
		registerEmptyFirstNameField = ConfigReader.getInstance().getValueForKey("registerEmptyFirstNameField");
		registerEmptyLastNameField = ConfigReader.getInstance().getValueForKey("registerEmptyLastNameField");
		registerExistingEmail = ConfigReader.getInstance().getValueForKey("registerExistingEmail");
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
			case REGISTERSUCCESS: {
				description = registerSuccess;
				responseType = "success";
				break;
			}
			case REGISTEREMPTYTITLE: {
				description = registerEmptyTitle;
				responseType = "error";
				break;
			}
			case REGISTEREMPTYFIRSTNAMEFIELD: {
				description = registerEmptyFirstNameField;
				responseType = "error";
				break;
			}
			case REGISTEREMPTYLASTNAMEFIELD: {
				description = registerEmptyLastNameField;
				responseType = "error";
				break;
			}
			case INVALIDEMAILFORMAT: {
				description = invalidEmailFormat;
				responseType = "error";
				break;
			}
			case REGISTEREXISTINGEMAIL: {
				description = registerExistingEmail;
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