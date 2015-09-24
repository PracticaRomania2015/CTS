package com.cts.communication;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.utils.ConfigReader;

@JsonSerialize
public class RecoveryResponse implements ResponseMessage {
	
	private static Logger logger = Logger.getLogger(RecoveryResponse.class.getName());

	private String type;
	private String description;
	
	private String emptyEmail;
	private String recoverySuccess;
	private String recoveryIncorrectEmail;
	private String unknownError;
	

	public RecoveryResponse() {

		initAll();
	}

	private void initAll() {
		
		emptyEmail = ConfigReader.getInstance().getValueForKey("emptyEmail");
		recoverySuccess = ConfigReader.getInstance().getValueForKey("recoverySuccess");
		recoveryIncorrectEmail = ConfigReader.getInstance().getValueForKey("recoveryIncorrectEmail");
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
			case RECOVERYSUCCESS: {
				type = "success";
				description = recoverySuccess;
				break;
			}
			case EMPTYEMAIL: {
				type = "error";
				description = emptyEmail;
				break;
			}
			case RECOVERYINCORRECTEMAIL: {
				type = "error";
				description = recoveryIncorrectEmail;
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
