package com.cts.communication;

import java.io.IOException;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cts.utils.ConfigReader;

@JsonSerialize
public class LoginError implements Error {

	private String emptyEmailFieldError;
	private String emptyPasswordFieldError;
	private String invalidEmailOrPasswordError;
	private String unknownError;

	private String description;

	private ConfigReader configReader;

	public LoginError() {
		initAll();
	}

	private void initAll() {
		configReader = new ConfigReader();

		emptyEmailFieldError = configReader.getValueForKey("emptyEmailFieldError");
		emptyPasswordFieldError = configReader.getValueForKey("emptyPasswordFieldError");
		invalidEmailOrPasswordError = configReader.getValueForKey("invalidEmailOrPasswordError");
		unknownError = configReader.getValueForKey("unknownError");

	}

	@Override
	public void initDescription(int errorCode) {
		switch (errorCode) {
		case 1: {
			description = emptyEmailFieldError;
			break;
		}
		case 2: {
			description = emptyPasswordFieldError;
			break;
		}
		case 3: {
			description = invalidEmailOrPasswordError;
			break;
		}

		default: {
			description = unknownError;
			break;
		}
		}
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String getErrorJson(int errorCode) {
		initDescription(errorCode);

		String errorMessageJson = "";

		try {
			errorMessageJson = objectMapper.writeValueAsString(this);
		} catch (IOException e) {
		}
		return errorMessageJson;
	}
}
