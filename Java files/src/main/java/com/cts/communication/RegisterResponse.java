package com.cts.communication;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.utils.ConfigReader;

@JsonSerialize
public class RegisterResponse implements ResponseMessage {
	
	private static Logger logger = Logger.getLogger(RegisterResponse.class.getName());

	private String type;
	private String description;
	
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
	
	public String getType() {
		
		return type;
	}
	
	public String getDescription() {
		
		return description;
	}
	
	@Override
	public void initDescription(ResponseValues responseValue) {
		
		switch (responseValue) {
			case REGISTERSUCCESS: {
				type = "success";
				description = registerSuccess;
				break;
			}
			case ERROR: {
				type = "error";
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
			case EMPTYPASSWORD: {
				description = emptyPassword;
				type = "error";
				break;
			}
			case INVALIDEMAILFORMAT: {
				type = "error";
				description = invalidEmailFormat;
				break;
			}
			case REGISTEREXISTINGEMAIL: {
				type = "error";
				description = registerExistingEmail;
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