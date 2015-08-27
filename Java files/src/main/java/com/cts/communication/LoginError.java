package com.cts.communication;

import com.cts.utils.ConfigReader;

public class LoginError extends Error {

	private String emptyEmailFieldError;
	private String emptyPasswordFieldError;
	private String invalidEmailOrPasswordError;
	private String unknownError;

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
	protected void initDescription(int errorCode) {
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
}
