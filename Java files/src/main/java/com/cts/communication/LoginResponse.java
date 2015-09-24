package com.cts.communication;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.utils.ConfigReader;

@JsonSerialize
public class LoginResponse implements ResponseMessage {
	
	private static Logger logger = Logger.getLogger(LoginResponse.class.getName());

	private Object data;
	private String type;
	private String description;
	
	private String emptyEmail;
	private String emptyPassword;
	private String loginInvalidCredentials;
	private String unknownError;
	

	public LoginResponse() {
		
		initAll();
	}
	
	public LoginResponse(Object data){
		
		this.data = data;
	}

	private void initAll() {
		
		emptyEmail = ConfigReader.getInstance().getValueForKey("emptyEmail");
		emptyPassword = ConfigReader.getInstance().getValueForKey("emptyPassword");
		loginInvalidCredentials = ConfigReader.getInstance().getValueForKey("loginInvalidCredentials");
		unknownError = ConfigReader.getInstance().getValueForKey("unknownError");
	}

	public Object getData() {
		
		return data;
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
			case EMPTYEMAIL: {
				type = "error";
				description = emptyEmail;
				break;
			}
			case EMPTYPASSWORD: {
				type = "error";
				description = emptyPassword;
				break;
			}
			case LOGININVALIDCREDENTIALS: {
				type = "error";
				description = loginInvalidCredentials;
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
