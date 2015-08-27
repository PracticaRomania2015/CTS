package com.cts.communication;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

import com.cts.utils.ConfigReader;

public class Success {

	private ConfigReader configReader = new ConfigReader();
	private ObjectMapper objectMapper = new ObjectMapper();

	private String description;
	private String registerSuccess;
	private String recoverySuccess;

	public Success() {

		initAll();
	}

	private void initAll() {

		registerSuccess = configReader.getValueForKey("success");
		recoverySuccess = configReader.getValueForKey("aNewPasswordWasSentToTheEmailSpecified");
	}

	public String getSuccessJson(int successCode) {

		initDescription(successCode);
		String successMessageJson = "";

		try {
			successMessageJson = objectMapper.writeValueAsString(this.description);
		} catch (IOException e) {
		}
		return successMessageJson;
	}

	private void initDescription(int successCode) {

		switch (successCode) {

		case 1: {

			description = registerSuccess;
			break;
		}
		case 2: {

			description = recoverySuccess;
			break;
		}
		default: {

			description = "";
			break;
		}
		}
	}
}
