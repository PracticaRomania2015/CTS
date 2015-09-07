package com.cts.communication;

import java.io.IOException;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.communication.ResponseMessage;
import com.cts.utils.ConfigReader;

@JsonSerialize
public class LoginResponse implements ResponseMessage {

	private String responseType;
	private String emptyEmailField;
	private String emptyPasswordField;
	private String loginInvalidCredentials;
	private String unknownError;

	private String description;

	private ConfigReader configReader;

	public LoginResponse() {
		initAll();
	}

	private void initAll() {
		configReader = new ConfigReader();
		
		emptyEmailField = configReader.getValueForKey("emptyEmailField");
		emptyPasswordField = configReader.getValueForKey("emptyPasswordField");
		loginInvalidCredentials = configReader.getValueForKey("loginInvalidCredentials");
		unknownError = configReader.getValueForKey("unknownError");
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
			case EMPTYEMAILFIELD: {
				description = emptyEmailField;
				responseType = "error";
				break;
			}
			case EMPTYPASSWORDFIELD: {
				description = emptyPasswordField;
				responseType = "error";
				break;
			}
			case LOGININVALIDCREDENTIALS: {
				description = loginInvalidCredentials;
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
