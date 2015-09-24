package com.cts.communication;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.utils.ConfigReader;

@JsonSerialize
public class UserPersonalDataResponse implements ResponseMessage {
	
	private static Logger logger = Logger.getLogger(UserPersonalDataResponse.class.getName());

	private String type;
	private String description;
	
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

	public UserPersonalDataResponse() {

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
			case EMPTYTITLE: {
				type = "error";
				description = emptyTitle;
				break;
			}
			case EMPTYFIRSTNAME: {
				type = "error";
				description = emptyFirstName;
				break;
			}
			case EMPTYLASTNAME: {
				type = "error";
				description = emptyLastName;
				break;
			}
			case EMPTYEMAIL: {
				type = "error";
				description = emptyEmail;
				break;
			}
			case INVALIDEMAILFORMAT: {
				type = "error";
				description = invalidEmailFormat;
				break;
			}
			case UPDATEUSEREMPTYOLDPASSWORD: {
				type = "error";
				description = updateUserEmptyOldPassword;
				break;
			}
			case UPDATEUSEREMPTYNEWPASSWORD: {
				type = "error";
				description = updateUserEmptyNewPassword;
				break;
			}
			case UPDATEUSERPASSWORDSNOTMATCHING: {
				type = "error";
				description = updateUserPasswordsNotMatching;
				break;
			}
			case UPDATEUSERSUCCESS: {
				type = "error";
				description = updateUserSuccess;
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
