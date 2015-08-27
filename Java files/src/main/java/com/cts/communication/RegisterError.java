package com.cts.communication;

import com.cts.utils.ConfigReader;

public class RegisterError extends Error {

	private String invalidEmailError;
	private String emptyFields;
	private String dbError;
	private String unknownError;
	private String existingEmailError;

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
	protected void initDescription(int errorCode) {
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

}