package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class RegisterResponse implements ResponseMessage {

	private String responseType;
	private String registerSuccess;
	private String emptyTitle;
	private String emptyFirstName;
	private String emptyLastName;
	private String emptyEmail;
	private String emptyPassword;
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
		registerExistingEmail = ConfigReader.getInstance().getValueForKey("registerExistingEmail");
		invalidEmailFormat = ConfigReader.getInstance().getValueForKey("invalidEmailFormat");
		emptyTitle = ConfigReader.getInstance().getValueForKey("emptyTitle");
		emptyFirstName = ConfigReader.getInstance().getValueForKey("emptyFirstName");
		emptyLastName = ConfigReader.getInstance().getValueForKey("emptyLastName");
		emptyEmail = ConfigReader.getInstance().getValueForKey("emptyEmail");
		emptyPassword = ConfigReader.getInstance().getValueForKey("emptyPassword");
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
			case ERROR: {
				responseType = "error";
				break;
			}
			case EMPTYTITLE: {
				description = emptyTitle;
				responseType = "error";
				break;
			}
			case EMPTYFIRSTNAME: {
				description = emptyFirstName;
				responseType = "error";
				break;
			}
			case EMPTYLASTNAME: {
				description = emptyLastName;
				responseType = "error";
				break;
			}
			case EMPTYEMAIL: {
				description = emptyEmail;
				responseType = "error";
				break;
			}
			case EMPTYPASSWORD: {
				description = emptyPassword;
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