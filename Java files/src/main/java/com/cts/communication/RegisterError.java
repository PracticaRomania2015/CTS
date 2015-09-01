package com.cts.communication;

import java.io.IOException;

import com.cts.utils.ConfigReader;

public class RegisterError implements Error {

	private String invalidEmailError;
	private String emptyFields;
	private String dbError;
	private String unknownError;
	private String existingEmailError;
	private String description;

	private ConfigReader configReader;

	public RegisterError() {
		initAll();
	}

	private void initAll() {
		configReader = new ConfigReader();
		
		invalidEmailError = configReader.getValueForKey("invalidEmailError");
		emptyFields = configReader.getValueForKey("emptyFields");
		dbError = configReader.getValueForKey("dbError");
		existingEmailError = configReader.getValueForKey("existingEmailError");
		unknownError = configReader.getValueForKey("unknownError");
	}

	@Override
	public void initDescription(int errorCode) {

		switch (errorCode) {

		case 5: {

			description = invalidEmailError;
			break;
		}
		case 6: {

			description = emptyFields;
			break;
		}
		case 7: {

			description = dbError;
			break;
		}
		case 9: {

			description = existingEmailError;
			break;
		}
		default: {

			description = unknownError;
			break;
		}
		}
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

	public String getDescription() {
		return description;
	}

}