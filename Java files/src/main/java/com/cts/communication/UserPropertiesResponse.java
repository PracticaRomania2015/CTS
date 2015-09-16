package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class UserPropertiesResponse implements ResponseMessage {

	private String responseType;
	private String emptyTitle;
	private String emptyFirstName;
	private String emptyLastName;
	private String emptyEmail;
	private String invalidEmailFormat;
	private String updateUserEmptyOldPassword;
	private String updateUserEmptyNewPassword;
	private String updateUserPasswordsNotMatching;
	private String updateUserSuccess;
	private String dbError;
	private String unknownError;
	private String description;

	public UserPropertiesResponse() {

		initAll();
	}

	private void initAll() {
		
		emptyTitle = ConfigReader.getInstance().getValueForKey("emptyTitle");
		emptyFirstName = ConfigReader.getInstance().getValueForKey("emptyFirstName");
		emptyLastName = ConfigReader.getInstance().getValueForKey("emptyLastName");
		emptyEmail = ConfigReader.getInstance().getValueForKey("emptyEmail");
		invalidEmailFormat = ConfigReader.getInstance().getValueForKey("invalidEmailFormat");
		updateUserEmptyOldPassword = ConfigReader.getInstance().getValueForKey("updateUserEmptyOldPassword");
		updateUserEmptyNewPassword = ConfigReader.getInstance().getValueForKey("updateUserEmptyNewPassword");
		updateUserPasswordsNotMatching = ConfigReader.getInstance().getValueForKey("updateUserPasswordsNotMatching");
		updateUserSuccess = ConfigReader.getInstance().getValueForKey("updateUserSuccess");
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
			case SUCCESS: {
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
			case INVALIDEMAILFORMAT: {
				description = invalidEmailFormat;
				responseType = "error";
				break;
			}
			case UPDATEUSEREMPTYOLDPASSWORD: {
				description = updateUserEmptyOldPassword;
				responseType = "error";
				break;
			}
			case UPDATEUSEREMPTYNEWPASSWORD: {
				description = updateUserEmptyNewPassword;
				responseType = "error";
				break;
			}
			case UPDATEUSERPASSWORDSNOTMATCHING: {
				description = updateUserPasswordsNotMatching;
				responseType = "error";
				break;
			}
			case UPDATEUSERSUCCESS: {
				description = updateUserSuccess;
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
