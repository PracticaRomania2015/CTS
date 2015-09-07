package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class RecoveryResponse implements ResponseMessage {

	private String responseType;
	private String recoverySuccess;
	private String recoveryIncorrectEmail;
	private String unknownError;
	private String description;

	private ConfigReader configReader;

	public RecoveryResponse() {

		initAll();
	}

	private void initAll() {
		configReader = new ConfigReader();
		
		recoverySuccess = configReader.getValueForKey("recoverySuccess");

		recoveryIncorrectEmail = configReader.getValueForKey("recoveryIncorrectEmail");
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
			case RECOVERYSUCCESS: {
				description = recoverySuccess;
				responseType = "success";
				break;
			}
			case RECOVERYINCORRECTEMAIL: {
				description = recoveryIncorrectEmail;
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
